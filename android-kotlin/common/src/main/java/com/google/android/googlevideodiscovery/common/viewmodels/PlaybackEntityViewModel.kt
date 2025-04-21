package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.engage.workers.PublishContinuationClusterWorker.Companion.publishContinuationCluster
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.MediaContentService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class PlaybackEntityViewModel @Inject constructor(
    private val mediaContentService: MediaContentService,
    private val continueWatchingService: ContinueWatchingService,
    @ApplicationContext private val context: Context,
    identityAndAccountManagementService: IdentityAndAccountManagementService,
) : ViewModel() {
    private val activeProfile = identityAndAccountManagementService.activeProfile

    private val _playbackEntity = MutableStateFlow<PlaybackEntity?>(null)
    val playbackEntity = _playbackEntity.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying = _isPlaying.asStateFlow()

    fun loadPlaybackEntity(entityId: String) {
        val activeProfileId = activeProfile.value?.id ?: return

        viewModelScope.launch {
            val continueWatchingEntity = continueWatchingService.getOne(
                entityId = entityId,
                profileId = activeProfileId
            )?.toPlaybackEntity()

            _playbackEntity.update {
                continueWatchingEntity ?: fetchPlaybackEntity(entityId)
            }
        }
    }

    fun updatePlaybackPosition(newPosition: Duration, reason: PublishContinuationClusterReason?) {
        _playbackEntity.update { old ->
            old?.copy(
                playbackPosition = newPosition
            )
        }
        reason?.let { updateContinueWatching(reason) }
    }

    fun updateIsPlaying(isPlaying: Boolean) {
        _isPlaying.update { isPlaying }
        if (!isPlaying) {
            updateContinueWatching(PublishContinuationClusterReason.VIDEO_STOPPED)
        }
    }

    private suspend fun fetchPlaybackEntity(entityId: String): PlaybackEntity? {
        return mediaContentService.findVideoEntityById(entityId)
            ?.toPlaybackEntity(playbackPosition = null)
    }

    private fun updateContinueWatching(reason: PublishContinuationClusterReason) {
        val entity = playbackEntity.value ?: return
        val activeProfileId = activeProfile.value?.id ?: return

        viewModelScope.launch {
            continueWatchingService.insertOrUpdateOne(
                entityId = entity.entityId,
                continueWatchingType = ContinueWatchingType.CONTINUE,
                profileId = activeProfileId,
                playbackPosition = entity.playbackPosition
            )
            context.publishContinuationCluster(profileId = activeProfileId, reason = reason)
        }
    }
}
