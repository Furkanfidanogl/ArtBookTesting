package com.furkanfidanoglu.artbooktesting.model

// JOSN verisi dizi olduğu için bu şekilde çekiyoruz
data class ImageResponse(
    val photos: List<ImageModel>
)

// Sadece almak istediğimiz veriyi çekiyoruz
data class ImageModel(
    val src: ImageSrc,
)

data class ImageSrc(
    val original: String,
    val large: String,
    val medium: String
)