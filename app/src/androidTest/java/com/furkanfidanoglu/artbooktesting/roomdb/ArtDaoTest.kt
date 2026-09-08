package com.furkanfidanoglu.artbooktesting.roomdb

import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import com.furkanfidanoglu.artbooktesting.model.Art
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtDaoTest {

    private lateinit var artDao: ArtDao
    private lateinit var database: ArtDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArtDatabase::class.java
        ).allowMainThreadQueries().build()
        artDao = database.artDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArtTesting_addsArtToList() = runTest {
        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        artDao.insertArt(art)
        //Burada advanceUntilIdle() kullanmadık.runTest içinde suspend fonksiyonu direkt çağırdığımızda, fonksiyon bitmeden alt satıra geçmez.

        val artList = artDao.getAllArts().first()

        assertThat(artList).contains(art)
    }

    @Test
    fun deleteArtById_removesArtFromList() = runTest {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        artDao.insertArt(art)
        artDao.deleteArtById(art.id)

        val artList = artDao.getAllArts().first()

        assertThat(artList).doesNotContain(art)

    }
}