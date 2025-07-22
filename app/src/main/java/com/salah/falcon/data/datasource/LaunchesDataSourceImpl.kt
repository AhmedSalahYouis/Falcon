package com.salah.falcon.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.salah.falcon.LaunchDetailsQuery
import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.core.error.DataError
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.core.error.IDataErrorProvider
import com.salah.falcon.data.mapper.IRemoteLaunchToLaunchDetailsMapper
import com.salah.falcon.data.mapper.IRemoteLaunchToLaunchSummaryMapper
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.model.LaunchSummary
import kotlinx.coroutines.flow.Flow

class LaunchesDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val logger: ITimberLogger,
    private val launchesMapper: IRemoteLaunchToLaunchSummaryMapper,
    private val launchDetailsMapper: IRemoteLaunchToLaunchDetailsMapper,
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

    override suspend fun getLaunchDetails(id: String): LaunchDetails {
        try {
            val requestQuery = LaunchDetailsQuery(
                launchId = id,
            )
            val response = apolloClient.query(requestQuery).execute()

            val launch = response.data?.launch

            return launch?.let {
                launchDetailsMapper.map(it)
            } ?: throw DataErrorException(DataError.Network.NOT_FOUND)
        } catch (exception: Exception) {
            throw DataErrorException(dataErrorProvider.fromThrowable(exception), exception)
        }
    }

}
