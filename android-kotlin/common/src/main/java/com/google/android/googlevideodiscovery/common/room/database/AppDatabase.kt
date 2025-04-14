package com.google.android.googlevideodiscovery.common.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import com.google.android.googlevideodiscovery.common.room.dto.DbAccount
import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile

@Database(entities = [DbAccount::class, DbAccountProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountProfileDao(): AccountProfileDao

}