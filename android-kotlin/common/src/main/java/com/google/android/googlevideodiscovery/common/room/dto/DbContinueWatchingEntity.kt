package com.google.android.googlevideodiscovery.common.room.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType
import com.google.android.googlevideodiscovery.common.models.EntityType
import com.google.android.googlevideodiscovery.common.models.VideoEntity
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Entity(
    tableName = "continue_watching_rows", foreignKeys = [
        ForeignKey(
            entity = DbAccountProfile::class,
            parentColumns = ["id"],
            childColumns = ["profileId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DbContinueWatchingEntity(
    @PrimaryKey val entityId: String,
    val entityType: EntityType,
    val playbackPositionMillis: Long,
    val lastEngagementTimeMillis: Long,
    val continueWatchingType: ContinueWatchingType,
    val profileId: String,
)

fun DbContinueWatchingEntity.toContinueWatchingEntity(videoEntity: VideoEntity): ContinueWatchingEntity {
    return ContinueWatchingEntity(
        entity = videoEntity,
        playbackPosition = playbackPositionMillis.toDuration(DurationUnit.MILLISECONDS),
        continueWatchingType = continueWatchingType,
        lastEngagementTimeMillis = lastEngagementTimeMillis,
        profileId = profileId,
    )
}
