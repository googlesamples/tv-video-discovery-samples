package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.room.dto.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    private val continueWatchingRepository: ContinueWatchingRepository,
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {

    val continueWatchingEntities = continueWatchingRepository.getContinueWatchingEntities()

    fun addTvEpisodeToContinueWatching(episodeId: String) {
        viewModelScope.launch {
            val tvEpisode = tvShowsService.fetchTvEpisodes().find { it.id == episodeId }
            tvEpisode?.let { mTvEpisode ->
                continueWatchingRepository.addToContinueWatching(
                    entity = mTvEpisode.toPlaybackEntity(),
                    continueWatchingType = ContinueWatchingType.CONTINUE
                )
            }
        }
    }

    fun addMovieToContinueWatching(movieId: String) {
        viewModelScope.launch {
            val movie = moviesService.fetchMovies().find { it.id == movieId }
            movie?.let { mMovie ->
                continueWatchingRepository.addToContinueWatching(
                    entity = mMovie.toPlaybackEntity(),
                    continueWatchingType = ContinueWatchingType.CONTINUE
                )
            }
        }
    }

    fun addNextEpisode(currentEpisodeId: String) {
        viewModelScope.launch {
            val currentEpisode = tvShowsService.fetchTvEpisodes().find { it.id == currentEpisodeId }

            currentEpisode?.nextEpisode?.let { nextEpisode ->
                addTvEpisodeToContinueWatching(episodeId = nextEpisode.id)
            }
        }
    }

    fun addNextMovie(currentMovieId: String) {
        viewModelScope.launch {
            val currentMovie = moviesService.fetchMovies().find { it.id == currentMovieId }

            currentMovie?.nextMovie?.let { nextMovie ->
                addMovieToContinueWatching(movieId = nextMovie.id)
            }
        }
    }

    fun updateContinueWatchingPosition(
        entityId: String,
        playbackPosition: Duration,
    ) {
        viewModelScope.launch {
            continueWatchingRepository.updatePlaybackPosition(
                entityId = entityId,
                playbackPosition = playbackPosition
            )
        }
    }

    fun removeFromContinueWatching(entityId: String) {
        viewModelScope.launch {
            continueWatchingRepository.removeFromContinueWatching(entityId)
        }
    }
}