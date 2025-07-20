package com.salah.falcon.domain.usecase

import androidx.paging.PagingData
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.repository.ILaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchListUseCase(
    private val launchesRepository: ILaunchesRepository,
) {

    suspend fun invoke(): Flow<PagingData<LaunchSummary>> = launchesRepository.getLaunchesList()

}
