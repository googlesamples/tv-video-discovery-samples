package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration

class ContinueWatchingService @Inject constructor(
    private val mediaContentService: MediaContentService,
    private val continueWatchingRepository: ContinueWatchingRepository
) {
    suspend fun getOne(entityId: String, profileId: String): ContinueWatchingEntity? {
        return continueWatchingRepository.getOne(entityId = entityId, profileId = profileId)
    }

    suspend fun insertOrUpdateOne(
        entityId: String,
        profileId: String,
        playbackPosition: Duration,
        continueWatchingType: ContinueWatchingType,
    ) {
        val videoEntity = mediaContentService.findVideoEntityById(entityId) ?: return

        val continueWatchingEntity = videoEntity.toContinueWatchingEntity(
            lastEngagementTime = Instant.now(),
            playbackPosition = playbackPosition,
            continueWatchingType = continueWatchingType,
            profileId = profileId,
        )

        continueWatchingRepository.insertOrUpdateOne(continueWatchingEntity)
    }
}