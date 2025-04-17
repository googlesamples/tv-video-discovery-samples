package com.google.android.googlevideodiscovery.common.engage.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.google.android.engage.service.AppEngagePublishClient
import com.google.android.googlevideodiscovery.common.engage.converters.buildEngagePublishContinuationRequest
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class PublishContinuationClusterWorker @Inject constructor(
    @ApplicationContext appContext: Context,
    params: WorkerParameters,
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val continueWatchingRepository: ContinueWatchingRepository,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
) : CoroutineWorker(
    appContext = appContext,
    params = params,
) {
    private val client = AppEngagePublishClient(appContext)

    override suspend fun doWork(): Result {
        val profileId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY) ?: return Result.failure()
        val profile = identityAndAccountManagementService.getProfileById(profileId)
            ?: return Result.failure()
        val continueWatchingEntities =
            continueWatchingRepository.getContinueWatchingEntities(profileId)
        val userConsentToSendDataToGoogle =
            syncAcrossDevicesConsentService.getSyncAcrossDevicesConsentValue(profile.account.id)

        val request = buildEngagePublishContinuationRequest(
            entities = continueWatchingEntities,
            syncAcrossDevices = userConsentToSendDataToGoogle,
            accountProfile = profile
        )

        val isServiceAvailable = client.isServiceAvailable().await()

        if (!isServiceAvailable) {
            return Result.retry()
        }

        client.publishContinuationCluster(request).await()

        return Result.success()
    }

    companion object {
        private const val INPUT_DATA_PROFILE_ID_KEY = "profile-id"

        fun Context.publishContinuationCluster(profileId: String) {
            val request = buildWorkRequest(profileId = profileId)

            WorkManager.getInstance(context = this)
                .enqueue(request)
        }

        private fun buildWorkRequest(profileId: String): OneTimeWorkRequest {
            val inputData = Data.Builder()
            inputData.putString(INPUT_DATA_PROFILE_ID_KEY, profileId)

            return OneTimeWorkRequestBuilder<PublishContinuationClusterWorker>()
                .setInputData(inputData.build())
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        }
    }

}