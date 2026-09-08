package com.furkanfidanoglu.artbooktesting.roomdb

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.furkanfidanoglu.artbooktesting.model.Art
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao {

    @Insert
    suspend fun insertArt(art: Art)

    @Query("SELECT * FROM art_table WHERE id = :artId")
    suspend fun getArtByID(artId: Int): Art?

    @Query("SELECT * FROM art_table ORDER BY id DESC")
    fun getAllArts(): Flow<List<Art>>

    @Query("DELETE FROM art_table WHERE id = :artId")
    suspend fun deleteArtById(artId: Int)

    @Query("DELETE FROM art_table")
    suspend fun deleteAllArts()
}