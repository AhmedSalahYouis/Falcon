package com.salah.falcon.presentation.launches.listing

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class LaunchesUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val launches: Flow<PagingData<LaunchSummary>> = emptyFlow(),
)
