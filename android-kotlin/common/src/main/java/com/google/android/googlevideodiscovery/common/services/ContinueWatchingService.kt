package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import com.google.android.googlevideodiscovery.common.room.repository.ContinueWatchingRepository
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ContinueWatchingService @Inject constructor(
    private val mediaContentService: MediaContentService,
    private val continueWatchingRepository: ContinueWatchingRepository
) {
    suspend fun getOne(entityId: String, profileId: String): ContinueWatchingEntity? {
        return continueWatchingRepository.getOne(entityId = entityId, profileId = profileId)
    }

    suspend fun getMany(profileId: String): List<ContinueWatchingEntity> {
        return continueWatchingRepository.getMany(profileId)
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

        if (isNearingEnd(continueWatchingEntity)) {
            continueWatchingRepository.removeOne(
                entityId = continueWatchingEntity.entity.id,
                profileId = profileId
            )
            val nextEntity = findNextContinueWatchingEntity(
                continueWatchingEntity = continueWatchingEntity,
                profileId = profileId
            )
            nextEntity?.let { continueWatchingRepository.insertOrUpdateOne(nextEntity) }
        } else {
            continueWatchingRepository.insertOrUpdateOne(continueWatchingEntity)
        }
    }

    suspend fun removeFromContinueWatching(continueWatchingEntity: ContinueWatchingEntity) {
        continueWatchingRepository.removeOne(
            entityId = continueWatchingEntity.entity.id,
            profileId = continueWatchingEntity.profileId
        )
    }

    private fun isNearingEnd(continueWatchingEntity: ContinueWatchingEntity): Boolean {
        val playbackPosition = continueWatchingEntity.playbackPosition
        val duration = continueWatchingEntity.entity.duration
        return duration - playbackPosition < END_THRESHOLD
    }

    private fun findNextContinueWatchingEntity(
        continueWatchingEntity: ContinueWatchingEntity,
        profileId: String
    ): ContinueWatchingEntity? = when (val entity = continueWatchingEntity.entity) {
        is MovieEntity -> entity.nextMovieEntity?.toContinueWatchingEntity(
            continueWatchingType = ContinueWatchingType.NEXT,
            profileId = profileId,
            playbackPosition = 0.seconds,
            lastEngagementTime = Instant.now()
        )

        is TvEpisodeEntity -> entity.nextEpisode?.toContinueWatchingEntity(
            continueWatchingType = ContinueWatchingType.NEXT,
            profileId = profileId,
            playbackPosition = 0.seconds,
            lastEngagementTime = Instant.now()
        )
    }


    companion object {
        private val END_THRESHOLD = 5.minutes
    }
}