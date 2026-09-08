package com.furkanfidanoglu.artbooktesting.repository

import com.furkanfidanoglu.artbooktesting.model.Art
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

class MockitoRepositoryTest {

    @Test
    fun mockRepository_returnsArtList() = runTest {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        val repository: ArtRepositoryInterface = mock()

        whenever(repository.getAllArts())
            .thenReturn(flowOf(listOf(art)))


        val result = repository.getAllArts().first()

        assertThat(result).contains(art)
    }

    @Test
    fun insertArt_callsRepositoryInsert() = runTest {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        val repository: ArtRepositoryInterface = mock()

        repository.insertArt(art)

        verify(repository).insertArt(art)
    }
}