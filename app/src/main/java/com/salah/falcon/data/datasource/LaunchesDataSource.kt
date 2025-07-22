package com.salah.falcon.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.salah.falcon.LaunchesQuery
import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.core.error.IDataErrorProvider
import com.salah.falcon.data.mapper.IRemoteLaunchToLaunchSummaryMapper
import com.salah.falcon.data.model.MapperException
import com.salah.falcon.domain.model.LaunchSummary

class LaunchesDataSource(
    private val apolloClient: ApolloClient,
    private val logger: ITimberLogger,
    private val launchesMapper: IRemoteLaunchToLaunchSummaryMapper,
    private val dataErrorProvider: IDataErrorProvider
) : PagingSource<String, LaunchSummary>() {

    override fun getRefreshKey(state: PagingState<String, LaunchSummary>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, LaunchSummary> {
        val cursor = params.key

        return try {
            val requestQuery = LaunchesQuery(after = Optional.presentIfNotNull(cursor))
            val response = apolloClient.query(requestQuery).execute()

            val launchesData = response.data?.launches

            val launchList = launchesData?.launches?.filterNotNull()?.mapNotNull {
                try {
                    launchesMapper.map(it)
                } catch (mapException: MapperException) {
                    logger.logError(
                        message = "Failed to map to LaunchSummary (launchId=${it.id})",
                        exception = mapException,
                    )
                    null
                }
            }

            val nextKey = if (launchesData?.hasMore == true) launchesData.cursor else null

            LoadResult.Page(
                data = launchList ?: emptyList(),
                prevKey = cursor,
                nextKey = nextKey
            )

        } catch (exception: Exception) {
            logger.logError("Failed to load launches", exception)
            LoadResult.Error(DataErrorException(dataErrorProvider.fromThrowable(exception), exception))
        }
    }
}
