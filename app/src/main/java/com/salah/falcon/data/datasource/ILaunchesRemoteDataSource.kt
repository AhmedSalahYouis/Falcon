package com.salah.falcon.data.datasource

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

interface ILaunchesRemoteDataSource {

    suspend fun getLaunchList(pageSize: Int): Flow<PagingData<LaunchSummary>>
    suspend fun getLaunchDetails(id: String): LaunchDetails
}
