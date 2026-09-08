package com.furkanfidanoglu.artbooktesting.repository

import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.model.ImageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRepository : ArtRepositoryInterface {

    private val _artList = MutableStateFlow<List<Art>>(emptyList())
    val artList = _artList.asStateFlow()

    override suspend fun searchImages(query: String): ImageResponse {
        return ImageResponse(emptyList())
    }

    override suspend fun insertArt(art: Art) {
        _artList.value += art
    }

    override fun getAllArts(): Flow<List<Art>> {
        return artList
    }

    override suspend fun getArtByID(id: Int): Art? {
        return artList.value.find { it.id == id }
    }

    override suspend fun deleteArtById(id: Int) {
        _artList.value = _artList.value.filter { it.id != id }
    }

    override suspend fun deleteAllArts() {
        _artList.value = emptyList()
    }
}