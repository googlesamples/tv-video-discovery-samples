package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.EntityType
import com.google.android.googlevideodiscovery.common.models.entityId
import com.google.android.googlevideodiscovery.common.models.toDbContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.dao.ContinueWatchingDao
import com.google.android.googlevideodiscovery.common.room.dto.toContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.MoviesService
import com.google.android.googlevideodiscovery.common.services.TvShowsService
import javax.inject.Inject
import kotlin.time.Duration

class ContinueWatchingRepository @Inject constructor(
    private val moviesService: MoviesService,
    private val tvShowsService: TvShowsService,
    private val continueWatchingDao: ContinueWatchingDao
) {
    suspend fun getContinueWatchingEntity(
        entityId: String,
        profileId: String?
    ): ContinueWatchingEntity? {
        if (profileId == null) {
            return null
        }
        return getContinueWatchingEntities(profileId).find { it.entityId == entityId }
    }

    suspend fun getContinueWatchingEntities(profileId: String): List<ContinueWatchingEntity> {
        val movies = moviesService.fetchMovies()
        val tvEpisodes = tvShowsService.fetchTvEpisodes()
        return continueWatchingDao.getContinueWatchingEntities(profileId = profileId)
            .mapNotNull { continueWatchingEntity ->
                val videoEntity = when (continueWatchingEntity.entityType) {
                    EntityType.MOVIE -> movies.find { it.id == continueWatchingEntity.entityId }
                    EntityType.TV_EPISODE -> tvEpisodes.find { it.id == continueWatchingEntity.entityId }
                }
                videoEntity?.let { continueWatchingEntity.toContinueWatchingEntity(videoEntity) }
            }
    }

    suspend fun addToContinueWatching(entity: ContinueWatchingEntity) {
        continueWatchingDao.addToContinueWatching(entity.toDbContinueWatchingEntity())
    }

    suspend fun updatePlaybackPosition(entityId: String, playbackPosition: Duration) {
        continueWatchingDao.updatePlaybackPosition(
            entityId = entityId,
            playbackPositionMillis = playbackPosition.inWholeMilliseconds
        )
    }

    suspend fun removeFromContinueWatching(entityId: String) {
        continueWatchingDao.removeFromContinueWatching(entityId)
    }
}