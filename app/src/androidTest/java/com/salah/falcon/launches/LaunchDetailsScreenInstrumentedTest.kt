package com.salah.falcon.launches

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salah.falcon.app.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchDetailsScreenInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun detailsScreen_displaysTitle() {
        composeTestRule.onNodeWithText("Details").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsLoadingState() {
        composeTestRule.onNodeWithText("Loading").assertExists()
    }

    @Test
    fun detailsScreen_showsErrorState() {
        composeTestRule.onNodeWithText("Unexpected error").assertExists()
    }

    @Test
    fun detailsScreen_backNavigation_works() {
        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()
        composeTestRule.onNodeWithText("Launches").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_displaysContentSections() {
        composeTestRule.onNodeWithText("Rocket").assertExists()
        composeTestRule.onNodeWithText("Mission").assertExists()
        composeTestRule.onNodeWithText("Site").assertExists()
    }
} 