package com.google.android.googlevideodiscovery.common.engage.workers

import android.content.Context
import android.widget.Toast
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
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.engage.converters.buildEngagePublishContinuationRequest
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

@HiltWorker
class PublishContinuationClusterWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val continueWatchingService: ContinueWatchingService,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
) : CoroutineWorker(
    appContext = appContext,
    params = params,
) {
    private val client = AppEngagePublishClient(appContext)

    override suspend fun doWork(): Result {
        val profileId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY) ?: return Result.failure()
        val publishReason =
            inputData.getString(INPUT_DATA_PUBLISH_REASON_KEY) ?: return Result.failure()

        val profile = identityAndAccountManagementService.getProfileById(profileId)
            ?: return Result.failure()
        val continueWatchingEntities = continueWatchingService.getMany(profileId)
        val userConsentToSendDataToGoogle =
            syncAcrossDevicesConsentService.getSyncAcrossDevicesConsentValue(profile.account.id)

        val request = buildEngagePublishContinuationRequest(
            entities = continueWatchingEntities,
            syncAcrossDevices = userConsentToSendDataToGoogle,
            accountProfile = profile,
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
        private const val INPUT_DATA_PUBLISH_REASON_KEY = "publish-reason"

        fun Context.publishContinuationCluster(
            profileId: String,
            reason: PublishContinuationClusterReason
        ) {
            val request = buildWorkRequest(profileId = profileId)

            Toast.makeText(
                this,
                "Invoking Engage SDK's publish continuation cluster. Reason: ${reason.message}",
                Toast.LENGTH_SHORT
            ).show()

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
