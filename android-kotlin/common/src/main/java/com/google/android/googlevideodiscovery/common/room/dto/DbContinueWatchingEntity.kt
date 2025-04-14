package com.google.android.googlevideodiscovery.common.room.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Entity(tableName = "continue_watching_rows")
data class DbContinueWatchingEntity(
    @PrimaryKey val entityId: String,
    val title: String,
    val releaseYear: Int,
    val durationMillis: Long,
    val playbackPositionMillis: Long,
    val lastEngagementTimeMillis: Long,
    val continueWatchingType: ContinueWatchingType,
)

enum class ContinueWatchingType {
    CONTINUE,
    NEXT,
}

fun DbContinueWatchingEntity.toPlaybackEntity() = PlaybackEntity(
    entityId = entityId,
    title = title,
    releaseYear = releaseYear,
    duration = durationMillis.toDuration(DurationUnit.MILLISECONDS),
    playbackPosition = playbackPositionMillis.toDuration(DurationUnit.MILLISECONDS),
)
