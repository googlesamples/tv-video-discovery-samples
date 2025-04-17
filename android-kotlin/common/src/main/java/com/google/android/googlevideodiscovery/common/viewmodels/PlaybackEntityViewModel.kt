package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import com.google.android.googlevideodiscovery.common.services.EngageInteractionService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class PlaybackEntityViewModel @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
    private val engageInteractionService: EngageInteractionService,
    private val continueWatchingRepository: ContinueWatchingRepository,
    identityAndAccountManagementService: IdentityAndAccountManagementService,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val activeProfile = identityAndAccountManagementService.activeProfile

    private val _playbackEntity = MutableStateFlow<PlaybackEntity?>(null)
    val playbackEntity = _playbackEntity.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying = _isPlaying.asStateFlow()

    fun loadPlaybackEntity(entityId: String) {
        viewModelScope.launch {
            val continueWatchingEntity = continueWatchingRepository.getContinueWatchingEntity(
                entityId,
                profileId = activeProfile.value?.id
            )?.toPlaybackEntity()
            _playbackEntity.value = continueWatchingEntity ?: fetchPlaybackEntity(entityId)
        }
    }

    fun updatePlaybackPosition(
        newPosition: Duration,
        reason: PlaybackUpdateReason
    ) {
        _playbackEntity.value = _playbackEntity.value?.copy(
            playbackPosition = newPosition
        )
    }

    fun updateIsPlaying(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

    private suspend fun fetchPlaybackEntity(entityId: String): PlaybackEntity? {
        // Instead of fetching your entire catalog in the ui, do this efficiently in your code.
        val movies = moviesService.fetchMovies()
        val tvEpisodes = tvShowsService.fetchTvEpisodes()

        val playbackEntities =
            movies.map { it.toPlaybackEntity() } + tvEpisodes.map { it.toPlaybackEntity() }

        return playbackEntities.find { it.entityId == entityId }
    }
}

enum class PlaybackUpdateReason {
    AUTO_UPDATE,
    USER_SCRUB,
}