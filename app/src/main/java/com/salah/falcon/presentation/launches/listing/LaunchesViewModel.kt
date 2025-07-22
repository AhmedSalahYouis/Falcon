package com.salah.falcon.presentation.launches.listing

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.salah.falcon.domain.usecase.GetLaunchListUseCase
import com.salah.falcon.presentation.MviAction
import com.salah.falcon.presentation.MviSideEffect
import com.salah.falcon.presentation.MviViewModel
import com.salah.falcon.presentation.launches.models.toUiModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LaunchesViewModel(
    private val launchListUseCase: GetLaunchListUseCase,
) : MviViewModel<LaunchesUiState, LaunchesViewModel.Action, LaunchesViewModel.SideEffect>() {

    init {
        fetchLaunches()
    }

    override fun createDefaultUiState() = LaunchesUiState(
        launches = emptyFlow(),
    )

    override fun handleAction(action: Action) {
        when (action) {
            Action.OnRetryFetchingClick -> {
                fetchLaunches()
            }

            is Action.OnLaunchClick -> {

            }
        }
    }

    private fun fetchLaunches() {

        viewModelScope.launch {
            val launchesFlow =
                launchListUseCase.invoke(GetLaunchListUseCase.Params(LAUNCHES_PAGE_SIZE))
                    .map { pagingData -> pagingData.map { it.toUiModel() } }
                    .cachedIn(viewModelScope)
            updateState {
                copy(
                    launches = launchesFlow,
                )
            }
        }
    }

    companion object {
        const val LAUNCHES_PAGE_SIZE = 20
    }

    sealed interface Action : MviAction {
        data class OnLaunchClick(val launchId: String) : Action
        data object OnRetryFetchingClick : Action
    }

    sealed interface SideEffect : MviSideEffect {
        data object Retry : SideEffect
    }

}
