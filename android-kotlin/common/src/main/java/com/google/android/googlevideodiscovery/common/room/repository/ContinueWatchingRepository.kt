package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toDbContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.dao.ContinueWatchingDao
import com.google.android.googlevideodiscovery.common.room.dto.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.room.dto.toPlaybackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration

class ContinueWatchingRepository @Inject constructor(
    private val continueWatchingDao: ContinueWatchingDao
) {
    fun getContinueWatchingEntities(): Flow<List<PlaybackEntity>> {
        return continueWatchingDao.getContinueWatchingEntities()
            .map { row -> row.map { entity -> entity.toPlaybackEntity() } }
    }

    suspend fun addToContinueWatching(
        entity: PlaybackEntity,
        continueWatchingType: ContinueWatchingType
    ) {
        continueWatchingDao.addToContinueWatching(
            entity.toDbContinueWatchingEntity(
                continueWatchingType = continueWatchingType,
                lastEngagementTime = Instant.now()
            )
        )
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