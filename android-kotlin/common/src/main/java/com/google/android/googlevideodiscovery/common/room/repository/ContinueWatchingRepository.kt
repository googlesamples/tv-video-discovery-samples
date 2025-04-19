package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.toDbContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.dao.ContinueWatchingDao
import com.google.android.googlevideodiscovery.common.room.dto.toContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.MediaContentService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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
        val videoEntity = mediaContentService.findVideoEntityById(entityId) ?: return null
        return continueWatchingDao.getOne(entityId = entityId, profileId = profileId)
            ?.toContinueWatchingEntity(
                videoEntity = videoEntity
            )
    }

    fun getAll(): Flow<List<ContinueWatchingEntity>> {
        return continueWatchingDao.getAll()
            .map { entities ->
                entities.mapNotNull { continueWatchingEntity ->
                    val entityId = continueWatchingEntity.entityId
                    val videoEntity = mediaContentService.findVideoEntityById(entityId)
                    videoEntity?.let {
                        continueWatchingEntity.toContinueWatchingEntity(videoEntity)
                    }
                }
            }
    }

    suspend fun getMany(profileId: String): List<ContinueWatchingEntity> {
        return continueWatchingDao.getMany(profileId = profileId)
            .mapNotNull { continueWatchingEntity ->
                val entityId = continueWatchingEntity.entityId
                val videoEntity = mediaContentService.findVideoEntityById(entityId)
                videoEntity?.let {
                    continueWatchingEntity.toContinueWatchingEntity(videoEntity)
                }
            }

    }

    suspend fun insertOrUpdateOne(entity: ContinueWatchingEntity) {
        continueWatchingDao.insertOrUpdate(entity.toDbContinueWatchingEntity())
    }

    suspend fun removeOne(continueWatchingEntity: ContinueWatchingEntity) {
        continueWatchingDao.removeFromContinueWatching(continueWatchingEntity.toDbContinueWatchingEntity())
    }
}