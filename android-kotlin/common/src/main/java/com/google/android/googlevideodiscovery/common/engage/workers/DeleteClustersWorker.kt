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
import com.google.android.googlevideodiscovery.common.engage.converters.DeleteReason
import com.google.android.googlevideodiscovery.common.engage.converters.buildEngageDeleteClustersRequest
import com.google.android.googlevideodiscovery.common.engage.converters.toEngageAccountProfile
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.google.android.engage.common.datamodel.AccountProfile as EngageAccountProfile

@HiltWorker
class DeleteClustersWorker @Inject constructor(
    @ApplicationContext appContext: Context,
    params: WorkerParameters,
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
) : ThrottledWorker(
    context = appContext,
    workerParameters = params,
) {
    private val client = AppEngagePublishClient(appContext)
    override suspend fun getThrottleKey(): String {
        val engageAccountProfile = readEngageAccountProfile()
        val id = engageAccountProfile?.profileId?.orNull() ?: engageAccountProfile?.accountId
        ?: "unknown"
        return buildUniqueThrottlingKey(id)
    }

    override suspend fun doThrottledWork(): Result {
        val engageAccountProfile = readEngageAccountProfile() ?: run {
            Log.i(TAG, "Failed to read profile id or account id from the input request")
            return Result.failure()
        }
        val rawDeleteReason =
            inputData.getString(INPUT_DATA_DELETE_REASON_KEY) ?: run {
                Log.i(TAG, "Failed to read delete reason from the input request")
                return Result.failure()
            }
        val deleteReason = DeleteReason.valueOf(rawDeleteReason)

        val userConsentToSendDataToGoogle =
            syncAcrossDevicesConsentService.getSyncAcrossDevicesConsentValue(engageAccountProfile.accountId)

        val request = buildEngageDeleteClustersRequest(
            syncAcrossDevices = userConsentToSendDataToGoogle,
            accountProfile = engageAccountProfile,
            deleteReason = deleteReason
        )

        Log.i(TAG, "Running Engage SDK's deleteClusters worker. Reason: ${deleteReason.name}")

        val isServiceAvailable = client.isServiceAvailable().await()

        if (!isServiceAvailable) {
            Log.i(TAG, "Engage service is unavailable")
            return Result.retry()
        }

        client.deleteClusters(request).await()

        return Result.success()
    }

    private suspend fun readEngageAccountProfile(): EngageAccountProfile? {
        val accountId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY)
        val profileId = inputData.getString(INPUT_DATA_PROFILE_ID_KEY)

        return when {
            accountId != null -> identityAndAccountManagementService.getAccountById(accountId)
                ?.toEngageAccountProfile()

            profileId != null -> identityAndAccountManagementService.getProfileById(profileId)
                ?.toEngageAccountProfile()

            else -> null
        }
    }

    companion object {
        private const val TAG = "DeleteClustersWorker"

        private const val INPUT_DATA_ACCOUNT_ID_KEY = "account-id"
        private const val INPUT_DATA_PROFILE_ID_KEY = "profile-id"
        private const val INPUT_DATA_DELETE_REASON_KEY = "delete-reason"

        fun Context.deleteClustersForEntireAccount(accountId: String, reason: DeleteReason) {
            val request = buildWorkRequest(reason = reason) {
                putString(INPUT_DATA_ACCOUNT_ID_KEY, accountId)
            }

            displayToast("Attempting to schedule deleteClusters worker. Reason: ${reason.name}")

            WorkManager.getInstance(context = this)
                .enqueueUniqueWork(
                    buildUniqueThrottlingKey(accountId),
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }

        fun Context.deleteClustersForProfile(profileId: String, reason: DeleteReason) {
            val request = buildWorkRequest(reason = reason) {
                putString(INPUT_DATA_PROFILE_ID_KEY, profileId)
            }

            displayToast("Attempting to schedule deleteClusters worker. Reason: ${reason.name}")

            WorkManager.getInstance(context = this)
                .enqueueUniqueWork(
                    buildUniqueThrottlingKey(profileId),
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }

        private fun buildUniqueThrottlingKey(id: String): String {
            return "delete_video_discovery:id=$id"
        }

        private fun buildWorkRequest(
            reason: DeleteReason,
            dataBuilder: Data.Builder.() -> Unit
        ): OneTimeWorkRequest {
            val inputData = Data.Builder()
            inputData.dataBuilder()
            inputData.putString(INPUT_DATA_DELETE_REASON_KEY, reason.name)

            return OneTimeWorkRequestBuilder<PublishContinuationClusterWorker>()
                .setInputData(inputData.build())
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        }

        private fun Context.displayToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}