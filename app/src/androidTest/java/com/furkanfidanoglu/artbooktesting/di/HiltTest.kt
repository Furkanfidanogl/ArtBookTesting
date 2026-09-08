package com.furkanfidanoglu.artbooktesting.di

import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.repository.ArtRepositoryInterface
import com.furkanfidanoglu.artbooktesting.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class HiltTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: ArtRepositoryInterface

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun repository_isFakeRepository() {
        assertThat(repository).isInstanceOf(FakeRepository::class.java)
    }

    @Test
    fun fakeRepository_addsArt() = runTest {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        repository.insertArt(art)

        val artList = repository.getAllArts().first()

        assertThat(artList).contains(art)
    }
}