package com.furkanfidanoglu.artbooktesting.di

import android.content.Context
import com.furkanfidanoglu.artbooktesting.roomdb.ArtDao
import com.furkanfidanoglu.artbooktesting.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): ArtDatabase {
        return ArtDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDao(db: ArtDatabase): ArtDao {
        return db.artDao()
    }
}