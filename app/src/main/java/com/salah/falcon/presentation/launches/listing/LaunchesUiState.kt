package com.salah.falcon.presentation.launches.listing

import androidx.paging.PagingData
import com.salah.falcon.presentation.launches.models.LaunchSummaryUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class LaunchesUiState(
    val launches: Flow<PagingData<LaunchSummaryUiModel>> = emptyFlow(),
)
