package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.VideoEntity
import javax.inject.Inject

class MediaContentService @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
) {
    suspend fun findVideoEntityById(entityId: String): VideoEntity? {
        // Instead of fetching your entire catalog in the app, do this efficiently in your code.
        return moviesService.fetchMovies().find { it.id == entityId }
            ?: tvShowsService.fetchTvEpisodes().find { it.id == entityId }
    }
}