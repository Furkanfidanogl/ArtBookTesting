package com.furkanfidanoglu.artbooktesting.roomdb

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.furkanfidanoglu.artbooktesting.model.Art

@Database(entities = [Art::class], version = 1, exportSchema = false)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao(): ArtDao

    companion object {
        @Volatile
        private var INSTANCE: ArtDatabase? = null

        fun getInstance(context: Context): ArtDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(context.applicationContext, ArtDatabase::class.java, "art_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}