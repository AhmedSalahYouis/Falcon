package com.salah.falcon.presentation.launches.listing

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.salah.falcon.domain.usecase.GetLaunchListUseCase
import com.salah.falcon.presentation.MviAction
import com.salah.falcon.presentation.MviSideEffect
import com.salah.falcon.presentation.MviViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class LaunchesViewModel(
    private val launchListUseCase: GetLaunchListUseCase,
) : MviViewModel<LaunchesUiState, LaunchesViewModel.Action, LaunchesViewModel.SideEffect>() {

    sealed interface Action : MviAction {
        data class OnLaunchClick(val launchId: String) : Action
        data object OnRetryClick : Action
    }

    sealed interface SideEffect : MviSideEffect {
        data object Retry : SideEffect
    }


    init {
        fetchLaunches()
    }

    override fun createDefaultUiState() = LaunchesUiState(
        isLoading = false,
        error = null,
        launches = emptyFlow(),
    )

    override fun handleAction(action: Action) {
        when (action) {
            Action.OnRetryClick -> {
                fetchLaunches()
            }
            is Action.OnLaunchClick -> {
            }
        }
    }

    private fun fetchLaunches() {
        updateState {
            copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            try {
                val launchesFlow = launchListUseCase.invoke().cachedIn(viewModelScope)
                updateState {
                    copy(
                        launches = launchesFlow,
                        isLoading = false,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                updateState {
                    copy(
                        isLoading = false,
                        error = e.message,
                    )
                }
            }
        }
    }
}
