package com.google.android.googlevideodiscovery.common.models

import com.google.android.googlevideodiscovery.common.room.dto.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity
import java.time.Instant
import kotlin.time.Duration

data class PlaybackEntity(
    val entityId: String,
    val title: String,
    val releaseYear: Int,
    val duration: Duration,
    val genre: String,
    var playbackPosition: Duration,
)

fun PlaybackEntity.toDbContinueWatchingEntity(
    continueWatchingType: ContinueWatchingType,
    lastEngagementTime: Instant
) = DbContinueWatchingEntity(
    entityId = entityId,
    title = title,
    releaseYear = releaseYear,
    durationMillis = duration.inWholeMilliseconds,
    genre = genre,
    playbackPositionMillis = playbackPosition.inWholeMilliseconds,
    continueWatchingType = continueWatchingType,
    lastEngagementTimeMillis = lastEngagementTime.toEpochMilli()
)
