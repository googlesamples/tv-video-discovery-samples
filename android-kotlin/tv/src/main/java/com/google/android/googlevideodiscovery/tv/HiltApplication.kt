package com.google.android.googlevideodiscovery.tv

import android.app.Application
import androidx.work.Configuration
import com.google.android.googlevideodiscovery.common.engage.factory.PublishContinuationClusterWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: PublishContinuationClusterWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

}
