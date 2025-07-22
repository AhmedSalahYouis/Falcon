package com.salah.falcon.presentation.launches.launchdetails

import com.salah.falcon.presentation.launches.models.LaunchDetailsUiModel
import com.salah.falcon.presentation.util.UiText

data class LaunchDetailsUiState(
    val isLoading: Boolean,
    val error: UiText?,
    val launchId: String,
    val details: LaunchDetailsUiModel?,
)
