package com.salah.falcon.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.core.error.IDataErrorProvider
import com.salah.falcon.data.mapper.IRemoteLaunchToLaunchSummaryMapper
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

class LaunchesDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val logger: ITimberLogger,
    private val launchesMapper: IRemoteLaunchToLaunchSummaryMapper,
    private val dataErrorProvider: IDataErrorProvider
) : ILaunchesRemoteDataSource {

    override suspend fun getLaunchList(pageSize: Int): Flow<PagingData<LaunchSummary>> =
        Pager(PagingConfig(pageSize = pageSize)) {
            LaunchesDataSource(
                apolloClient = apolloClient,
                logger = logger,
                launchesMapper = launchesMapper,
                dataErrorProvider = dataErrorProvider
            )
        }.flow

}
