package com.google.android.googlevideodiscovery.common.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.android.googlevideodiscovery.common.room.dto.DbContinueWatchingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinueWatchingDao {

    @Query("SELECT * FROM continue_watching_rows WHERE profileId = :profileId AND entityId = :entityId")
    suspend fun getOne(entityId: String, profileId: String): DbContinueWatchingEntity?

    @Query("SELECT * FROM continue_watching_rows ORDER BY lastEngagementTimeMillis DESC")
    fun getAll(): Flow<List<DbContinueWatchingEntity>>

    @Query("SELECT * FROM continue_watching_rows WHERE profileId = :profileId ORDER BY lastEngagementTimeMillis DESC")
    suspend fun getMany(profileId: String): List<DbContinueWatchingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: DbContinueWatchingEntity)

    @Delete
    suspend fun removeFromContinueWatching(continueWatchingEntity: DbContinueWatchingEntity)
}