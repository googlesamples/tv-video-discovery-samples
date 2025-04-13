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

@HiltViewModel
class PlaybackEntityViewModel @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {
    private val _playbackEntity = MutableStateFlow<PlaybackEntity?>(null)
    val playbackEntity = _playbackEntity.asStateFlow()

    fun loadPlaybackEntity(entityId: String) {
        viewModelScope.launch {
            // Instead of fetching your entire catalog in the ui, do this efficiently in your code.
            val movies = moviesService.fetchMovies()
            val tvEpisodes = tvShowsService.fetchTvEpisodes()

            val playbackEntities =
                movies.map { it.toPlaybackEntity() } + tvEpisodes.map { it.toPlaybackEntity() }

            _playbackEntity.value = playbackEntities.find { it.entityId == entityId }
        }
    }
}