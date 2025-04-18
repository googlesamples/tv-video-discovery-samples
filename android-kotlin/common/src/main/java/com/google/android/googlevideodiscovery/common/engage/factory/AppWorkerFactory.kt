package com.google.android.googlevideodiscovery.common.engage.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
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
        if (workerClassName == "PublishContinuationClusterWorker") {
            return PublishContinuationClusterWorker(
                appContext = appContext,
                params = workerParameters,
                identityAndAccountManagementService = identityAndAccountManagementService,
                continueWatchingService = continueWatchingService,
                syncAcrossDevicesConsentService = syncAcrossDevicesConsentService
            )
        }
        return null
    }
}