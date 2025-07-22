package com.salah.falcon.data.repository

import androidx.paging.PagingData
import com.salah.falcon.data.datasource.ILaunchesRemoteDataSource
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.repository.ILaunchesRepository
import kotlinx.coroutines.flow.Flow

class LaunchesRepository(
    private val remoteDataSource: ILaunchesRemoteDataSource,
) : ILaunchesRepository {

    override suspend fun getLaunchesList(pageSize: Int): Flow<PagingData<LaunchSummary>> = remoteDataSource.getLaunchList(pageSize)

}
