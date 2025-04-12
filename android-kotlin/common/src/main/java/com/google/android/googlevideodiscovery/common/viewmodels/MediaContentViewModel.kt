package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.TvEpisode
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MediaContentViewModel(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies
        .onStart { loadMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _tvEpisodes = MutableStateFlow<List<TvEpisode>>(emptyList())
    val tvEpisodes = _tvEpisodes
        .onStart { loadTvEpisodes() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun loadMovies() {
        viewModelScope.launch {
            _movies.value = moviesService.fetchMovies()
        }
    }

    private fun loadTvEpisodes() {
        viewModelScope.launch {
            _tvEpisodes.value = tvShowsService.fetchTvEpisodes().reversed()
        }
    }
}