package com.furkanfidanoglu.artbooktesting.navigation

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable
    data object HomeScreen : Navigation()

    @Serializable
    data class DetailScreen(val id : Int) : Navigation()

    @Serializable
    data object AddScreen : Navigation()

    @Serializable
    data object SearchScreen : Navigation()
}