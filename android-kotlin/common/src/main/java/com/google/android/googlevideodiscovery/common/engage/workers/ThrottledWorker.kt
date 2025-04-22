package com.google.android.googlevideodiscovery.common.engage.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import androidx.core.content.edit

abstract class ThrottledWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    // Child classes should provide a unique key (e.g. "upload:account_123")
    abstract suspend fun getThrottleKey(): String

    // Child classes implement this to do their work
    abstract suspend fun doThrottledWork(): Result

    open suspend fun getThrottleDuration(): Duration {
        return THROTTLE_DURATION
    }

    override suspend fun doWork(): Result {
        val prefs = applicationContext.getSharedPreferences(
            THROTTLED_WORKER_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )

        val lastRunTime = prefs.getLong(buildLastRunKey(), 0L)
        val currentTime = Instant.now().toEpochMilli()
        val timeSinceLastRun = currentTime - lastRunTime

        return if (timeSinceLastRun < getThrottleDuration().inWholeMilliseconds) {
            Log.i(
                TAG,
                "Throttled ${getThrottleKey()} for ${
                    getThrottleDuration() - timeSinceLastRun.toDuration(DurationUnit.MILLISECONDS)
                }"
            )
            Result.retry()
        } else {
            prefs.edit { putLong(buildLastRunKey(), currentTime) }
            doThrottledWork()
        }
    }

    private suspend fun buildLastRunKey(): String {
        return "last_run_${getThrottleKey()}"
    }

    companion object {
        private const val TAG = "ThrottledWorker"

        private val THROTTLE_DURATION = 10.seconds
        private const val THROTTLED_WORKER_PREFERENCES_KEY = "throttled_worker_prefs"
    }
}