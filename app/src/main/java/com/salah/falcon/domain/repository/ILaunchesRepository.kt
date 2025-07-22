package com.salah.falcon.domain.repository

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

interface ILaunchesRepository {
    
    suspend fun getLaunchesList(pageSize: Int): Flow<PagingData<LaunchSummary>>

    suspend fun getLaunchDetails(id: String): LaunchDetails

}
