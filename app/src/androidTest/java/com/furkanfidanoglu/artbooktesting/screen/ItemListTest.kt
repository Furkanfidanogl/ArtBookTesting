package com.furkanfidanoglu.artbooktesting.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.furkanfidanoglu.artbooktesting.TestActivity
import com.furkanfidanoglu.artbooktesting.model.Art
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class ItemListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()


    @Test
    fun itemList_whenEmpty_displaysEmptyMessage() {
        composeTestRule.setContent {
            ItemList(artList = emptyList(), goToDetailScreen = {}, onDelete = {})
        }

        composeTestRule
            .onNodeWithText("No artwork yet")
            .assertIsDisplayed()
    }

    @Test
    fun itemList_whenNotEmpty_displaysArt() {
        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        composeTestRule.setContent {
            ItemList(artList = listOf(art), goToDetailScreen = {}, onDelete = {})
        }

        composeTestRule
            .onNodeWithText("MacBook")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Furkan")
            .assertIsDisplayed()
    }


    @Test
    fun itemList_whenArtClicked_returnsCorrectId() {
        val art = Art(id = 1, name = "MacBook", artistName = "Furkan", year = 2026, image = "")

        var clickedArtId: Int? = null

        composeTestRule.setContent {
            ItemList(artList = listOf(art), goToDetailScreen = { id ->
                    clickedArtId = id
                },
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithText("MacBook")
            .performClick()

        assertThat(clickedArtId).isEqualTo(1)
    }
}