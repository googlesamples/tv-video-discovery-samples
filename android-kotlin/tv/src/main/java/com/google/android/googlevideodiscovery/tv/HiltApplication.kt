package com.google.android.googlevideodiscovery.tv

import android.app.Application
import androidx.work.Configuration
import com.google.android.googlevideodiscovery.common.engage.factory.AppWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

}
