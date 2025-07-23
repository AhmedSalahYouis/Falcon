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
        // ProgressView is a CircularProgressIndicator, so check for a progress indicator
        composeTestRule.onNode(hasTestTag("ProgressIndicator")).assertExists()
    }

    @Test
    fun launchesScreen_showsErrorState() {
        // ErrorView displays error text and a retry button
        composeTestRule.onNodeWithText("unknown error occurred").assertExists()
        composeTestRule.onNodeWithText("Retry").assertExists()
    }

    @Test
    fun launchesScreen_showsEmptyState() {
        // EmptyView displays the empty message
        composeTestRule.onNodeWithText("Space is clear. No Launches at the moment").assertExists()
    }

    @Test
    fun launchesScreen_displaysList() {
        // Assumes at least one launch is present, check for a known rocket name
        composeTestRule.onAllNodesWithText("Falcon 9").assertAny(hasText("Falcon 9"))
    }

    @Test
    fun launchesScreen_clickItem_navigatesToDetails() {
        // Click on a launch item and check navigation to details
        composeTestRule.onAllNodesWithText("Falcon 9").onFirst().performClick()
        composeTestRule.onNodeWithText("Rocket").assertExists()
    }

    @Test
    fun launchesScreen_canScrollList() {
        // Try to scroll the list (if enough items)
        composeTestRule.onNode(hasScrollAction()).performScrollToIndex(5)
    }

    @Test
    fun launchesScreen_retryOnError() {
        // ErrorView has a retry button
        composeTestRule.onNodeWithText("Retry").performClick()
        // After retry, check if progress indicator is shown
        composeTestRule.onNode(hasTestTag("ProgressIndicator")).assertExists()
    }

    @Test
    fun launchesScreen_stateTransitions() {
        // Simulate state transitions if possible (loading -> error -> content)
        // This requires test-only state injection or a test build variant
        // For now, check that all states can be displayed
        composeTestRule.onNode(hasTestTag("ProgressIndicator")).assertExists()
        composeTestRule.onNodeWithText("unknown error occurred").assertExists()
        composeTestRule.onAllNodesWithText("Falcon 9").assertAny(hasText("Falcon 9"))
    }
}