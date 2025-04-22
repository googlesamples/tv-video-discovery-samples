package com.google.android.googlevideodiscovery.common.engage.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
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
) : ThrottledWorker(
    context = appContext,
    workerParameters = params,
) {
    private val client = AppEngagePublishClient(appContext)
    override suspend fun getThrottleKey(): String {
        val profileId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY) ?: "unknown"
        return buildUniqueThrottlingKey(profileId)
    }

    override suspend fun doThrottledWork(): Result {
        val profileId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY) ?: run {
            Log.i(TAG, "Profile id not found in input request")
            return Result.failure()
        }
        val rawPublishReason =
            inputData.getString(INPUT_DATA_PUBLISH_REASON_KEY) ?: run {
                Log.i(TAG, "Publish reason not found in input request")
                return Result.failure()
            }
        val reason = PublishContinuationClusterReason.valueOf(rawPublishReason)

        Log.i(
            TAG,
            "Running Engage SDK's publish continuation cluster worker. Reason: ${reason.message}"
        )

        val profile = identityAndAccountManagementService.getProfileById(profileId)
            ?: run {
                Log.i(TAG, "Unable to fetch profile information")
                return Result.failure()
            }

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
            Log.i(TAG, "Engage service unavailable")
            return Result.retry()
        }

        client.publishContinuationCluster(request).await()

        return Result.success()
    }

    companion object {
        private const val TAG = "PublishContinuationClusterWorker"

        private const val INPUT_DATA_PROFILE_ID_KEY = "profile-id"
        private const val INPUT_DATA_PUBLISH_REASON_KEY = "publish-reason"

        fun Context.publishContinuationCluster(
            profileId: String,
            reason: PublishContinuationClusterReason
        ) {
            val request = buildWorkRequest(profileId = profileId, reason = reason)

            Toast.makeText(
                applicationContext,
                "Attempting to schedule publish continuation cluster. Reason: ${reason.message}",
                Toast.LENGTH_SHORT
            ).show()

            WorkManager.getInstance(context = this)
                .enqueueUniqueWork(
                    buildUniqueThrottlingKey(profileId),
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }

        private fun buildUniqueThrottlingKey(profileId: String): String {
            return "publish_continue_watching:profileId=$profileId"
        }

        private fun buildWorkRequest(
            profileId: String,
            reason: PublishContinuationClusterReason
        ): OneTimeWorkRequest {
            val inputData = Data.Builder()
            inputData.putString(INPUT_DATA_PROFILE_ID_KEY, profileId)
            inputData.putString(INPUT_DATA_PUBLISH_REASON_KEY, reason.name)

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
