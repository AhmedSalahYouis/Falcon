package com.salah.falcon.launches

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salah.falcon.R
import com.salah.falcon.core.error.DataError
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.presentation.launches.launchdetails.LaunchDetailsScreen
import com.salah.falcon.presentation.launches.launchdetails.LaunchDetailsUiState
import com.salah.falcon.presentation.launches.launchdetails.LaunchDetailsViewModel
import com.salah.falcon.presentation.launches.models.LaunchDetailsUiModel
import com.salah.falcon.presentation.launches.models.MissionUiModel
import com.salah.falcon.presentation.launches.models.RocketUiModel
import com.salah.falcon.presentation.util.extensions.toUiText
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchDetailsScreenInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val launchId = "99"

    @RelaxedMockK
    lateinit var mockViewModel: LaunchDetailsViewModel

    private val _sideEffectFlow = MutableSharedFlow<LaunchDetailsViewModel.SideEffect>()

    @Before
    fun setup() {
        MockKAnnotations.init(this) // Initialize mocks

        every { mockViewModel.sideEffect } returns _sideEffectFlow
        // For handleAction, we typically just want to verify it's called
        every { mockViewModel.handleAction(any()) } answers { /* Do nothing */ }
    }

    @Test
    fun detailsScreen_displaysTitle() {
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchDetailsUiState(
                isLoading = false,
                error = null,
                launchId = launchId,
                details = null
            )
        )

        composeTestRule.setContent {
            LaunchDetailsScreen(
                launchId = "99",
                navigateBack = {}
            )
        }
        mockViewModel.onArgsReceived(launchId)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.title, launchId)).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsLoadingState() {
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchDetailsUiState(
                isLoading = true,
                error = null,
                launchId = "91",
                details = null
            )
        )

        composeTestRule.setContent {
            LaunchDetailsScreen(
                launchId = launchId,
                navigateBack = {}
            )
        }
        mockViewModel.onArgsReceived(launchId)
        composeTestRule.onNodeWithTag("ProgressView").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsErrorState() {
        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchDetailsUiState(
                isLoading = false,
                error = DataErrorException(DataError.Network.UNEXPECTED_ERROR).error.toUiText(),
                launchId = "29",
                details = null
            )
        )

        composeTestRule.setContent {
            LaunchDetailsScreen(
                launchId = "29",
                navigateBack = {}
            )
        }
        mockViewModel.onArgsReceived("29")

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.unexpected_error))
            .assertIsDisplayed()
    }

    @Test
    fun detailsScreen_displaysContentSections() {

        every { mockViewModel.uiStateFlow } returns MutableStateFlow(
            LaunchDetailsUiState(
                isLoading = false,
                error = null,
                launchId = "49",
                details = LaunchDetailsUiModel(
                    id = "49",
                    rocket = RocketUiModel(launchId, "Rocket", "SFK-2"),
                    mission = MissionUiModel(
                        "Starlink-15", ""
                    ),
                    site = "Cape Canaveral"
                )
            )
        )

        composeTestRule.setContent {
            LaunchDetailsScreen(
                launchId = "49",
                navigateBack = {}
            )
        }
        mockViewModel.onArgsReceived("49")
        composeTestRule.onNodeWithText("Rocket").assertIsDisplayed()
        composeTestRule.onNodeWithText("Starlink-15").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cape Canaveral").assertIsDisplayed()
    }
} 