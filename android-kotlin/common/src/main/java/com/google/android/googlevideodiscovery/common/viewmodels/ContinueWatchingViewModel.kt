package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.models.toContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    private val continueWatchingRepository: ContinueWatchingRepository,
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {

    private val _continueWatchingEntities =
        MutableStateFlow<List<ContinueWatchingEntity>>(emptyList())
    val continueWatchingEntities = _continueWatchingEntities.asStateFlow()

    fun loadContinueWatchingEntities(profileId: String) {
        viewModelScope.launch {
            _continueWatchingEntities.value =
                continueWatchingRepository.getContinueWatchingEntities(profileId = profileId)
        }
    }

    fun addTvEpisodeToContinueWatching(episodeId: String, profileId: String) {
        viewModelScope.launch {
            val tvEpisode = tvShowsService.fetchTvEpisodes().find { it.id == episodeId }
            tvEpisode?.let { mTvEpisode ->
                continueWatchingRepository.addToContinueWatching(
                    entity = mTvEpisode.toContinueWatchingEntity(
                        continueWatchingType = ContinueWatchingType.CONTINUE,
                        profileId = profileId,
                        playbackPosition = 0.seconds,
                        lastEngagementTime = Instant.now(),
                    )
                )
            }
        }
    }

    fun addMovieToContinueWatching(movieId: String, profileId: String) {
        viewModelScope.launch {
            val movie = moviesService.fetchMovies().find { it.id == movieId }
            movie?.let { mMovie ->
                continueWatchingRepository.addToContinueWatching(
                    entity = mMovie.toContinueWatchingEntity(
                        continueWatchingType = ContinueWatchingType.CONTINUE,
                        profileId = profileId,
                        playbackPosition = 0.seconds,
                        lastEngagementTime = Instant.now(),
                    )
                )
            }
        }
    }

    fun addNextEpisode(currentEpisodeId: String, profileId: String) {
        viewModelScope.launch {
            val currentEpisode = tvShowsService.fetchTvEpisodes().find { it.id == currentEpisodeId }

            currentEpisode?.nextEpisode?.let { nextEpisode ->
                addTvEpisodeToContinueWatching(episodeId = nextEpisode.id, profileId = profileId)
            }
        }
    }

    fun addNextMovie(currentMovieId: String, profileId: String) {
        viewModelScope.launch {
            val currentMovie = moviesService.fetchMovies().find { it.id == currentMovieId }

            currentMovie?.nextMovie?.let { nextMovie ->
                addMovieToContinueWatching(movieId = nextMovie.id, profileId = profileId)
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