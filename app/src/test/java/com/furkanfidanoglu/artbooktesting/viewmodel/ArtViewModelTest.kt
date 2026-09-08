package com.furkanfidanoglu.artbooktesting.viewmodel

import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // ArtViewModel init bloğunda getAllArts() çağrılıyor.
        // getAllArts() -> viewModelScope.launch kullandığı için Dispatchers.Main ister.
        // Local unit testte gerçek Android Main thread olmadığı için
        // Main dispatcher'ı TestDispatcher ile değiştiriyoruz.
        Dispatchers.setMain(testDispatcher)

        val repository = FakeRepository()
        viewModel = ArtViewModel(repository)
    }

    @After
    fun tearDown() {
        // Test bittikten sonra Main dispatcher'ı eski haline döndürüyoruz.
        Dispatchers.resetMain()
    }

    @Test
    fun insertArt_addsArtToList() = runTest(testDispatcher) {
        // runTest: Coroutine kullanan test için kontrollü coroutine test ortamı oluşturur.
        val art = Art(id = 1,name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        viewModel.insertArt(art)
        advanceUntilIdle()     // Test dispatcher’daki bekleyen tüm coroutine işlerini bitirir.

        assertThat(viewModel.artList.value).contains(art)
    }

    @Test
    fun getArtByID_returnsCorrectArt() = runTest(testDispatcher) {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        viewModel.insertArt(art)
        advanceUntilIdle()

        viewModel.getArtByID(1)
        advanceUntilIdle()

        assertThat(viewModel.selectedArt.value).isEqualTo(art)
    }

    @Test
    fun getAllArts_returnsAllArts() = runTest(testDispatcher) {

        val art1 = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")
        val art2 = Art(id = 2, name = "iPad", artistName = "Furkan", year = 2025, image = "")

        viewModel.insertArt(art1)
        viewModel.insertArt(art2)

        advanceUntilIdle()

        assertThat(viewModel.artList.value).containsExactly(art1, art2)
    }

    @Test
    fun deleteArtById_removesArtFromList() = runTest(testDispatcher) {

        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        viewModel.insertArt(art)
        advanceUntilIdle()

        viewModel.deleteArtById(1)
        advanceUntilIdle()

        assertThat(viewModel.artList.value).doesNotContain(art)
    }

    @Test
    fun deleteAllArts_clearsArtList() = runTest(testDispatcher) {

        val art1 = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")
        val art2 = Art(id = 2, name = "iPad", artistName = "Furkan", year = 2025, image = "")

        viewModel.insertArt(art1)
        viewModel.insertArt(art2)
        advanceUntilIdle()

        viewModel.deleteAllArts()
        advanceUntilIdle()

        assertThat(viewModel.artList.value).isEmpty()
    }
}


/*
setMain()
→ ViewModel'in kullandığı Main'i test ortamına uyarlar

runTest()
→ test fonksiyonunun coroutine ortamını kurar

advanceUntilIdle()
→ TestDispatcher'da bekleyen tüm coroutine işlerini tamamlayıp testi sonra devam ettirir.
*/