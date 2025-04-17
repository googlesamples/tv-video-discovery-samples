package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.entityId
import com.google.android.googlevideodiscovery.common.models.toDbContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.dao.ContinueWatchingDao
import com.google.android.googlevideodiscovery.common.room.dto.toContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.MediaContentService
import javax.inject.Inject
import kotlin.time.Duration

class ContinueWatchingRepository @Inject constructor(
    private val mediaContentService: MediaContentService,
    private val continueWatchingDao: ContinueWatchingDao
) {
    suspend fun getOne(
        entityId: String,
        profileId: String?
    ): ContinueWatchingEntity? {
        if (profileId == null) {
            return null
        }
        return getMany(profileId).find { it.entityId == entityId }
    }

    suspend fun getMany(profileId: String): List<ContinueWatchingEntity> {
        return continueWatchingDao.getContinueWatchingEntities(profileId = profileId)
            .mapNotNull { continueWatchingEntity ->
                val videoEntity =
                    mediaContentService.findVideoEntityById(continueWatchingEntity.entityId)
                videoEntity?.let { continueWatchingEntity.toContinueWatchingEntity(videoEntity) }
            }
    }

    suspend fun insertOrUpdateOne(entity: ContinueWatchingEntity) {
        continueWatchingDao.insertOrUpdate(entity.toDbContinueWatchingEntity())
    }

    suspend fun removeOne(entityId: String) {
        continueWatchingDao.removeFromContinueWatching(entityId)
    }

    suspend fun updatePlaybackPosition(entityId: String, playbackPosition: Duration) {
        continueWatchingDao.updatePlaybackPosition(
            entityId = entityId,
            playbackPositionMillis = playbackPosition.inWholeMilliseconds
        )
    }
}