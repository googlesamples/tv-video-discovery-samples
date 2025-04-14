package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PlaybackEntityViewModel @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {
    private val _playbackEntity = MutableStateFlow<PlaybackEntity?>(null)
    val playbackEntity = _playbackEntity.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying = _isPlaying.asStateFlow()

    fun loadPlaybackEntity(entityId: String, initialPlaybackPosition: Duration?) {
        viewModelScope.launch {
            // Instead of fetching your entire catalog in the ui, do this efficiently in your code.
            val movies = moviesService.fetchMovies()
            val tvEpisodes = tvShowsService.fetchTvEpisodes()

            val playbackEntities =
                movies.map { it.toPlaybackEntity() } + tvEpisodes.map { it.toPlaybackEntity() }

            _playbackEntity.value = playbackEntities.find { it.entityId == entityId }?.copy(
                playbackPosition = initialPlaybackPosition ?: 0.seconds
            )
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
}

enum class PlaybackUpdateReason {
    AUTO_UPDATE,
    USER_SCRUB,
}