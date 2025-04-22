package com.google.android.googlevideodiscovery.common.engage.factory

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.android.googlevideodiscovery.common.engage.workers.DeleteClustersWorker
import com.google.android.googlevideodiscovery.common.engage.workers.PublishContinuationClusterWorker
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import javax.inject.Inject

class AppWorkerFactory @Inject constructor(
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val continueWatchingService: ContinueWatchingService,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        if (workerClassName.endsWith("PublishContinuationClusterWorker")) {
            return PublishContinuationClusterWorker(
                appContext = appContext,
                params = workerParameters,
                identityAndAccountManagementService = identityAndAccountManagementService,
                continueWatchingService = continueWatchingService,
                syncAcrossDevicesConsentService = syncAcrossDevicesConsentService
            )
        }
        if (workerClassName.endsWith("DeleteClustersWorker")) {
            return DeleteClustersWorker(
                appContext = appContext,
                params = workerParameters,
                identityAndAccountManagementService = identityAndAccountManagementService,
                syncAcrossDevicesConsentService = syncAcrossDevicesConsentService
            )
        }
        return null
    }
}