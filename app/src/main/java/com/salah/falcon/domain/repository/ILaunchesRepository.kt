package com.salah.falcon.domain.repository

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

interface ILaunchesRepository {
    
    suspend fun getLaunchesList(): Flow<PagingData<LaunchSummary>>
}
