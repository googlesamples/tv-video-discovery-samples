package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaContentViewModel @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies = _movies
        .onStart { _movies.value = moviesService.fetchMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _tvEpisodes = MutableStateFlow<List<TvEpisodeEntity>>(emptyList())
    val tvEpisodes = _tvEpisodes
        .onStart { _tvEpisodes.value = tvShowsService.fetchTvEpisodes() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}