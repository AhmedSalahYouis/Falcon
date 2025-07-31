package com.salah.falcon.launches

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.salah.falcon.R
import com.salah.falcon.core.error.DataError
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.presentation.launches.listing.LaunchesScreen
import com.salah.falcon.presentation.launches.listing.LaunchesUiState
import com.salah.falcon.presentation.launches.listing.LaunchesViewModel
import com.salah.falcon.presentation.launches.listing.compose.LaunchItem
import com.salah.falcon.presentation.launches.models.LaunchSummaryUiModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LaunchesScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @RelaxedMockK
    lateinit var mockViewModel: LaunchesViewModel

    private val launches = listOf(
        LaunchSummaryUiModel(
            id = "1",
            rocketName = "Falcon 9",
            missionName = "Starlink-15",
            missionPatchImageURL = "",
        ),
        LaunchSummaryUiModel(
            id = "2",
            rocketName = "Falcon 1",
            missionName = "Starlink-15",
            missionPatchImageURL = "",
        )
    )

    private val _sideEffectFlow = MutableSharedFlow<LaunchesViewModel.SideEffect>()

    @Before
    fun setup() {
        MockKAnnotations.init(this) // Initialize mocks

        every { mockViewModel.sideEffect } returns _sideEffectFlow
        // For handleAction, we typically just want to verify it's called
        every { mockViewModel.handleAction(any()) } answers { /* Do nothing */ }
    }

    @Test
    fun launchesScreen_displaysLoadingState_onInitialLoad() = runTest {

        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchesUiState(
                launches = flowOf(
                    PagingData.from(
                        launches, LoadStates(
                            refresh = LoadState.Loading,
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false)
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            LaunchesScreen(
                viewModel = mockViewModel,
                navigateToDetailsScreen = { /* No-op for this test */ }
            )
        }

        // Act & Assert
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.launches_screen_title))
            .assertIsDisplayed()
    }

    @Test
    fun launchesScreen_displaysLaunchesList_whenDataIsAvailable() = runTest {
        // Arrange
        val testLaunches = listOf(
            LaunchSummaryUiModel("1", "Falcon 9", "Starlink-1", "url1"),
            LaunchSummaryUiModel("2", "Falcon Heavy", "Mars Mission", "url2")
        )

        // Update the ViewModel's state with data
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchesUiState(
                launches = flowOf(
                    PagingData.from(
                        testLaunches, LoadStates(
                            refresh = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false)
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            LaunchesScreen(
                viewModel = mockViewModel,
                navigateToDetailsScreen = { /* No-op */ }
            )
        }

        // Act & Assert
        composeTestRule.onNodeWithText("Starlink-1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Falcon Heavy").assertIsDisplayed()

        // Verify LaunchItem's testTag
        composeTestRule.onAllNodesWithTag("LaunchItem")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("LaunchItem")[1].assertIsDisplayed()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.launches_screen_title))
            .assertIsDisplayed()
    }

    @Test
    fun launchesScreen_displaysEmptyView_whenListIsEmpty() = runTest {

        // Update the ViewModel's state with empty data
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchesUiState(
                launches = flowOf(
                    PagingData.from(
                        emptyList(), LoadStates(
                            refresh = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false)
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            LaunchesScreen(
                viewModel = mockViewModel,
                navigateToDetailsScreen = { /* No-op */ }
            )
        }

        // Act & Assert
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.empty_launches_message))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmptyView")
            .assertIsDisplayed() // Assuming EmptyView has this tag
    }

    @Test
    fun launchesScreen_displaysErrorView_onError() = runTest {
        // Arrange
        // Update the ViewModel's state with empty data
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchesUiState(
                launches = flowOf(
                    PagingData.from(
                        emptyList(), LoadStates(
                            refresh = LoadState.Error(DataErrorException(DataError.Network.UNEXPECTED_ERROR)),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false)
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            LaunchesScreen(
                viewModel = mockViewModel,
                navigateToDetailsScreen = { /* No-op */ }
            )
        }

        // Act & Assert
        // Depending on how ErrorView displays the error, adjust the assertion
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.unexpected_error))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("ErrorView")
            .assertIsDisplayed() // Assuming ErrorView has this tag
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.retry_button_label))
            .assertIsDisplayed()

    }

    @Test
    fun launchesScreen_displaysAppendLoading_whenScrolling() = runTest {

        // Arrange
        // Update the ViewModel's state with empty data
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchesUiState(
                launches = flowOf(
                    PagingData.from(
                        launches, LoadStates(
                            refresh = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.Loading
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            LaunchesScreen(
                viewModel = mockViewModel,
                navigateToDetailsScreen = { /* No-op */ }
            )
        }
        // Act & Assert
        composeTestRule.onNodeWithTag("PagingProgressView")
            .assertIsDisplayed() // Assuming PagingProgressView has this tag
    }

    @Test
    fun launchItem_displaysCorrectInfo_andIsClickable() = runTest {
        // Arrange
        val testLaunch = LaunchSummaryUiModel(
            id = "test-id",
            rocketName = "Test Rocket",
            missionName = "Test Mission",
            missionPatchImageURL = "test_url"
        )
        val onClickMock: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            LaunchItem(launchDetails = testLaunch, onClick = onClickMock)
        }


        // Act & Assert
        composeTestRule.onNodeWithText("Test Rocket").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Mission").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(
                R.string.listing_launch_item_image_content_description,
                "Test Mission"
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag("LaunchItem").assertIsDisplayed().performClick()

        // Verify click listener was invoked
        verify(exactly = 1) { onClickMock.invoke() }
    }
}