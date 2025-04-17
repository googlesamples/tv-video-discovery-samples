package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.MediaContentService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class PlaybackEntityViewModel @Inject constructor(
    private val mediaContentService: MediaContentService,
    private val continueWatchingService: ContinueWatchingService,
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

            _playbackEntity.value = continueWatchingEntity ?: fetchPlaybackEntity(entityId)
        }
    }

    fun updatePlaybackPosition(newPosition: Duration) {
        _playbackEntity.value = _playbackEntity.value?.copy(
            playbackPosition = newPosition
        )
    }

    fun updateIsPlaying(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
        if (!isPlaying) {
            updateContinueWatching()
        }
    }

    private suspend fun fetchPlaybackEntity(entityId: String): PlaybackEntity? {
        return mediaContentService.findVideoEntityById(entityId)?.toPlaybackEntity()
    }

    private fun updateContinueWatching() {
        val entity = playbackEntity.value ?: return
        val activeProfileId = activeProfile.value?.id ?: return

        viewModelScope.launch {
            continueWatchingService.insertOrUpdateOne(
                entityId = entity.entityId,
                continueWatchingType = ContinueWatchingType.CONTINUE,
                profileId = activeProfileId,
                playbackPosition = entity.playbackPosition
            )
        }
    }
}
