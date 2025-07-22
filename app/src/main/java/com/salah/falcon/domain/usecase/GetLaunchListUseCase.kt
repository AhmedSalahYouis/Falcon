package com.salah.falcon.domain.usecase

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.repository.ILaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchListUseCase(
    private val launchesRepository: ILaunchesRepository,
) {

    suspend fun invoke(params: Params): Flow<PagingData<LaunchSummary>> {
        // Domain specific business logic can be added here.
        // For example, filtering launches based on certain criteria,
        // transforming data, or applying specific rules before returning the data.
        return launchesRepository.getLaunchesList(params.pageSize)
    }

    data class Params(val pageSize: Int)
}
