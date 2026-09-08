package com.furkanfidanoglu.artbooktesting.repository

import com.furkanfidanoglu.artbooktesting.roomdb.ArtDao
import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.remote.ImageAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val api: ImageAPI , private val dao: ArtDao) : ArtRepositoryInterface {

    //API
    override suspend fun searchImages(query: String) = api.searchImages(query)


    //Room - DAO
    override suspend fun insertArt(art: Art) {
        dao.insertArt(art)
    }

    override fun getAllArts(): Flow<List<Art>> {
        return dao.getAllArts()
    }

    override suspend fun getArtByID(id: Int) = dao.getArtByID(id)

    override suspend fun deleteArtById(id: Int) {
        dao.deleteArtById(id)
    }

    override suspend fun deleteAllArts() {
        dao.deleteAllArts()
    }

}