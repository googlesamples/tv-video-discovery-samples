package com.google.android.googlevideodiscovery.common.models

import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity
import kotlin.time.Duration

data class ContinueWatchingEntity(
    val entity: VideoEntity,
    val playbackPosition: Duration,
    val lastEngagementTimeMillis: Long,
    val continueWatchingType: ContinueWatchingType,
    val profileId: String,
)

val ContinueWatchingEntity.entityId
    get() = run {
        when (entity) {
            is Movie -> entity.id
            is TvEpisode -> entity.id
        }
    }

fun ContinueWatchingEntity.toDbContinueWatchingEntity(): DbContinueWatchingEntity {
    val entityType = when (entity) {
        is Movie -> EntityType.MOVIE
        is TvEpisode -> EntityType.TV_EPISODE
    }
    return DbContinueWatchingEntity(
        entityId = entityId,
        entityType = entityType,
        playbackPositionMillis = playbackPosition.inWholeMilliseconds,
        lastEngagementTimeMillis = lastEngagementTimeMillis,
        continueWatchingType = continueWatchingType,
        profileId = profileId,
    )
}

fun ContinueWatchingEntity.toPlaybackEntity() = when (entity) {
    is Movie -> entity.toPlaybackEntity(playbackPosition = playbackPosition)
    is TvEpisode -> entity.toPlaybackEntity(playbackPosition = playbackPosition)
}
