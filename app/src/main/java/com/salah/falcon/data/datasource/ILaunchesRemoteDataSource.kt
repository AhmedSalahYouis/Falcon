package com.salah.falcon.data.datasource

import androidx.paging.PagingData
import com.salah.falcon.data.model.DataSourceException
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

interface ILaunchesRemoteDataSource {

    @Throws(DataSourceException::class)
    suspend fun getLaunchList(): Flow<PagingData<LaunchSummary>>
}
