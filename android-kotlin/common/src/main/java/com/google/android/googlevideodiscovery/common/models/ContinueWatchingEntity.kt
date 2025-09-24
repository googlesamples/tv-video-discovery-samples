package com.google.android.googlevideodiscovery.common.models

import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity
import kotlin.time.Duration

data class ContinueWatchingEntity(
    val entity: VideoEntity,
    val playbackPosition: Duration,
    val lastEngagementTimeMillis: Long,
    val continueWatchingType: ContinueWatchingType,
    val profileId: String,
) {
    val releaseYear: Int
        get() = when (entity) {
            is MovieEntity -> entity.releaseYear
            is TvEpisodeEntity -> entity.releaseYear
            is VideoClipEntity -> entity.releaseYear
            else -> throw IllegalStateException("Unsupported video type for Continue Watching: $entity")
        }
    val duration: Duration
        get() = when (entity) {
            is MovieEntity -> entity.duration
            is TvEpisodeEntity -> entity.duration
            is VideoClipEntity -> entity.duration
            else -> throw IllegalStateException("Unsupported video type for Continue Watching: $entity")
        }

    val genre: String
        get() = when (entity) {
            is MovieEntity -> entity.genre
            is TvEpisodeEntity -> entity.genre
            is VideoClipEntity -> entity.genre
            else -> throw IllegalStateException("Unsupported video type for Continue Watching: $entity")
        }

    val progressPercent: Float
        get() = playbackPosition.inWholeMilliseconds.toFloat() / duration.inWholeMilliseconds
}

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
