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

fun ContinueWatchingEntity.toDbContinueWatchingEntity(): DbContinueWatchingEntity {
    return DbContinueWatchingEntity(
        entityId = entity.id,
        entityType = entity.type,
        playbackPositionMillis = playbackPosition.inWholeMilliseconds,
        lastEngagementTimeMillis = lastEngagementTimeMillis,
        continueWatchingType = continueWatchingType,
        profileId = profileId,
    )
}

fun ContinueWatchingEntity.toPlaybackEntity() =
    entity.toPlaybackEntity(playbackPosition = playbackPosition)
