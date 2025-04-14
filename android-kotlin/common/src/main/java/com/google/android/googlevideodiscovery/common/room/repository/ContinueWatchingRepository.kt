package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.toDbContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.room.dao.ContinueWatchingDao
import com.google.android.googlevideodiscovery.common.room.dto.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.room.dto.toPlaybackEntity
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration

class ContinueWatchingRepository @Inject constructor(
    private val continueWatchingDao: ContinueWatchingDao
) {
    suspend fun getContinueWatchingEntities(profileId: String): List<PlaybackEntity> {
        return continueWatchingDao.getContinueWatchingEntities(profileId = profileId)
            .map { entity -> entity.toPlaybackEntity() }
    }

    suspend fun addToContinueWatching(
        profileId: String,
        entity: PlaybackEntity,
        continueWatchingType: ContinueWatchingType
    ) {
        continueWatchingDao.addToContinueWatching(
            entity.toDbContinueWatchingEntity(
                continueWatchingType = continueWatchingType,
                lastEngagementTime = Instant.now(),
                profileId = profileId,
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