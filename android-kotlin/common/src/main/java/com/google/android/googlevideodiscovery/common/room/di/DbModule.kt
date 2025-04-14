package com.google.android.googlevideodiscovery.common.room.di

import android.content.Context
import androidx.room.Room
import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import com.google.android.googlevideodiscovery.common.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideAccountProfileDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room
            .databaseBuilder(
                app,
                AppDatabase::class.java,
                "google-tv-video-discovery-sample"
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountProfileDao(db: AppDatabase): AccountProfileDao {
        return db.accountProfileDao()
    }

}