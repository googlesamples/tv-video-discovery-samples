package com.google.android.googlevideodiscovery.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity

@Dao
interface ContinueWatchingDao {

    @Query("SELECT * FROM continue_watching_rows WHERE profileId = :profileId ORDER BY lastEngagementTimeMillis DESC")
    suspend fun getMany(profileId: String): List<DbContinueWatchingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: DbContinueWatchingEntity)

    @Query("DELETE FROM continue_watching_rows WHERE entityId = :entityId AND profileId = :profileId")
    suspend fun removeFromContinueWatching(entityId: String, profileId: String)
}