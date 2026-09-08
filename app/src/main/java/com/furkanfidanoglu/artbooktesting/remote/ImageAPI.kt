package com.furkanfidanoglu.artbooktesting.remote

import com.furkanfidanoglu.artbooktesting.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageAPI {

    // "https://api.pexels.com/v1/search?query=nature&per_page=1"


    // "query=nature" kısmını endPoint'ten çıkartıyoruz
    @GET("search?per_page=30")
    suspend fun searchImages(@Query("query") query: String): ImageResponse
}