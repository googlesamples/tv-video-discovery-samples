package com.google.android.googlevideodiscovery.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity

@Dao
interface ContinueWatchingDao {

    @Query("SELECT * FROM continue_watching_rows WHERE profileId = :profileId ORDER BY lastEngagementTimeMillis DESC")
    suspend fun getContinueWatchingEntities(profileId: String): List<DbContinueWatchingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToContinueWatching(entity: DbContinueWatchingEntity)

    @Query("UPDATE continue_watching_rows SET playbackPositionMillis = :playbackPositionMillis WHERE entityId = :entityId")
    suspend fun updatePlaybackPosition(entityId: String, playbackPositionMillis: Long)

    @Query("DELETE FROM continue_watching_rows WHERE entityId = :entityId")
    suspend fun removeFromContinueWatching(entityId: String)
}