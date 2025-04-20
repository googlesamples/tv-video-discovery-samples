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
import com.google.android.googlevideodiscovery.common.engage.converters.DeleteReason
import com.google.android.googlevideodiscovery.common.engage.converters.buildEngageDeleteClustersRequest
import com.google.android.googlevideodiscovery.common.engage.converters.toEngageAccountProfile
import com.google.android.googlevideodiscovery.common.engage.workers.DeleteClustersWorker.Companion.deleteClustersForEntireAccount
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
) : CoroutineWorker(
    appContext = appContext,
    params = params,
) {
    private val client = AppEngagePublishClient(appContext)

    override suspend fun doWork(): Result {
        val engageAccountProfile = readEngageAccountProfile() ?: return Result.failure()
        val deleteReason =
            inputData.getString(INPUT_DATA_DELETE_REASON_KEY) ?: return Result.failure()

        val userConsentToSendDataToGoogle =
            syncAcrossDevicesConsentService.getSyncAcrossDevicesConsentValue(engageAccountProfile.accountId)

        val request = buildEngageDeleteClustersRequest(
            syncAcrossDevices = userConsentToSendDataToGoogle,
            accountProfile = engageAccountProfile,
            deleteReason = DeleteReason.valueOf(deleteReason)
        )

        val isServiceAvailable = client.isServiceAvailable().await()

        if (!isServiceAvailable) {
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
        private const val INPUT_DATA_ACCOUNT_ID_KEY = "account-id"
        private const val INPUT_DATA_PROFILE_ID_KEY = "profile-id"
        private const val INPUT_DATA_DELETE_REASON_KEY = "delete-reason"

        fun Context.deleteClustersForEntireAccount(accountId: String, reason: DeleteReason) {
            val request = buildWorkRequest(deleteReason = reason) {
                putString(INPUT_DATA_ACCOUNT_ID_KEY, accountId)
            }

            displayToast("Invoking Engage SDK's deleteClusters. Reason: ${reason.name}")

            WorkManager.getInstance(context = this)
                .enqueue(request)
        }

        fun Context.deleteClustersForProfile(profileId: String, deleteReason: DeleteReason) {
            val request = buildWorkRequest(deleteReason = deleteReason) {
                putString(INPUT_DATA_PROFILE_ID_KEY, profileId)
            }

            displayToast("Invoking Engage SDK's deleteClusters. Reason: ${deleteReason.name}")

            WorkManager.getInstance(context = this)
                .enqueue(request)
        }

        private fun buildWorkRequest(
            deleteReason: DeleteReason,
            dataBuilder: Data.Builder.() -> Unit
        ): OneTimeWorkRequest {
            val inputData = Data.Builder()
            inputData.dataBuilder()
            inputData.putString(INPUT_DATA_DELETE_REASON_KEY, deleteReason.name)

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