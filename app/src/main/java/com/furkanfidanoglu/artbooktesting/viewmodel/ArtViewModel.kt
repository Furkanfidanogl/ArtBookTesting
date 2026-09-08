package com.furkanfidanoglu.artbooktesting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.model.ImageSrc
import com.furkanfidanoglu.artbooktesting.repository.ArtRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(private val repository: ArtRepositoryInterface) : ViewModel(){

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()
    fun updateSearchInput(input: String) {
        _searchInput.value = input
    }
    fun clearAddScreenState() {
        _selectedImage.value = ""
        _searchInput.value = ""
        _imageList.value = emptyList()
    }

    private val _selectedImage = MutableStateFlow("")
    val selectedImage = _selectedImage.asStateFlow()
    fun updateSelectedImage(image: String) {
        _selectedImage.value = image
    }


    private val _imageList = MutableStateFlow<List<ImageSrc>>(emptyList())
    val imageList = _imageList.asStateFlow()


    private val _artList = MutableStateFlow<List<Art>>(emptyList())
    val artList = _artList.asStateFlow()


    private val _selectedArt = MutableStateFlow<Art?>(null)
    val selectedArt = _selectedArt.asStateFlow()


    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    fun searchImages(query: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = repository.searchImages(query)
                _imageList.value = response.photos.map { it.src }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }


    init{
        getAllArts()
    }

    fun insertArt(art: Art){
        viewModelScope.launch {
            repository.insertArt(art)
        }
    }

    fun getAllArts(){
        viewModelScope.launch {
            repository.getAllArts().collect { arts ->
                _artList.value = arts
            }
        }
    }

    fun getArtByID(id: Int){
        viewModelScope.launch {
            val art = repository.getArtByID(id)
            _selectedArt.value = art
        }
    }

    fun deleteArtById(id: Int){
        viewModelScope.launch {
            repository.deleteArtById(id)
        }
    }

    fun deleteAllArts(){
        viewModelScope.launch {
            repository.deleteAllArts()
        }
    }

}