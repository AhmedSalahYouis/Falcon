package com.salah.falcon.launches

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salah.falcon.app.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchesScreenInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launchesScreen_displaysTitle() {
        composeTestRule.onNodeWithText("Launches").assertIsDisplayed()
    }

    @Test
    fun launchesScreen_showsLoadingState() {
        // Simulate loading state if possible
        composeTestRule.onNodeWithText("Loading").assertExists()
    }

    @Test
    fun launchesScreen_showsErrorState() {
        // Simulate error state if possible
        composeTestRule.onNodeWithText("Unexpected error").assertExists()
    }

    @Test
    fun launchesScreen_showsEmptyState() {
        // Simulate empty state if possible
        composeTestRule.onNodeWithText("No launches found").assertExists()
    }

    @Test
    fun launchesScreen_clickItem_navigatesToDetails() {
        // Click on a launch item and check navigation
        composeTestRule.onAllNodesWithText("Falcon 9").onFirst().performClick()
        composeTestRule.onNodeWithText("Details").assertIsDisplayed()
    }
} 