package com.salah.falcon.domain.usecase

import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.repository.ILaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLaunchDetailsUseCase(
    private val launchesRepository: ILaunchesRepository,
) {
    fun invoke(id: String): Flow<LaunchDetails> = flow {
            val launchDetailsResult = launchesRepository.getLaunchDetails(id)
            emit(launchDetailsResult)
    }
}
