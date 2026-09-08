package com.furkanfidanoglu.artbooktesting.repository

import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.model.ImageResponse
import kotlinx.coroutines.flow.Flow

interface ArtRepositoryInterface {

    suspend fun searchImages(query: String): ImageResponse

    suspend fun insertArt(art: Art)

    fun getAllArts(): Flow<List<Art>>

    suspend fun getArtByID(id: Int): Art?

    suspend fun deleteArtById(id: Int)

    suspend fun deleteAllArts()
}