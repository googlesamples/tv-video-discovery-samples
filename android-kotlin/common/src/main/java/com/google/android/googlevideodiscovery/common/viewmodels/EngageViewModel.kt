package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.android.googlevideodiscovery.common.engage.workers.PublishContinuationClusterWorker.Companion.publishContinuationCluster

@HiltViewModel
class EngageViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    fun publishContinuationCluster(profileId: String, reason: PublishContinueWatchingReason) {
        displayToast("Publishing continue watching. Reason: ${reason.message}")
        context.publishContinuationCluster(profileId)
    }

    private fun displayToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

enum class PublishContinueWatchingReason(internal val message: String) {
    PROFILE_SELECTION("Profile was selected"),
    VIDEO_PAUSED("User paused playing video"),
    VIDEO_STOPPED("User stopped playing video"),
    APP_EXITED_WHILE_VIDEO_PLAYBACK_IN_PROGRESS("User exited app when video playback was in progress"),
    ENTITY_REMOVED_FROM_CONTINUE_WATCHING_ROW("Entity was removed from the Continue Watching row"),
    NEXT_MOVIE_IN_SERIES("User completed watching a movie which is part of series and next movie should be in Continue Watching row"),
    NEXT_TV_EPISODE_IN_SERIES("User completed watching tv episode and next episode should be in Continue Watching row"),
}