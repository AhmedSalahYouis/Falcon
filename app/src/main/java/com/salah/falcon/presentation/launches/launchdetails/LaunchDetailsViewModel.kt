package com.salah.falcon.presentation.launches.launchdetails

import androidx.lifecycle.viewModelScope
import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.common.network.result.Result
import com.salah.falcon.common.network.result.asResult
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.domain.usecase.GetLaunchDetailsUseCase
import com.salah.falcon.presentation.MviAction
import com.salah.falcon.presentation.MviSideEffect
import com.salah.falcon.presentation.MviViewModel
import com.salah.falcon.presentation.launches.models.toUiModel
import com.salah.falcon.presentation.util.extensions.toUiText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val detailsUseCase: GetLaunchDetailsUseCase,
    private val logger: ITimberLogger,
) : MviViewModel<LaunchDetailsUiState, LaunchDetailsViewModel.Action, LaunchDetailsViewModel.SideEffect>() {

    sealed interface Action : MviAction {
        data object OnBackClick : Action
        data object OnRetryClick : Action
    }

    sealed interface SideEffect : MviSideEffect {
        data object NavigateBack : SideEffect
    }


    fun onArgsReceived(
        launchId: String,
    ) {
        // fetch data only if launchId changed
        if (uiStateFlow.value.launchId != launchId) {
            updateState {
                copy(
                    launchId = launchId,
                )
            }
            fetchDetails(launchId)
        }
    }

    override fun createDefaultUiState(): LaunchDetailsUiState = LaunchDetailsUiState(
        launchId = "",
        isLoading = true,
        error = null,
        details = null,
    )

    override fun handleAction(action: Action) {
        when (action) {
            Action.OnBackClick -> sendSideEffect(SideEffect.NavigateBack)
            Action.OnRetryClick -> {
                if (uiStateFlow.value.launchId.isNotBlank()) {
                    fetchDetails(uiStateFlow.value.launchId)
                } else {
                    logger.logError("Unexpected state, launchId is empty")
                }
            }
        }
    }

    private fun fetchDetails(launchId: String) {
        updateState {
            copy(
                isLoading = true,
                error = null,
                details = null,
            )
        }

        viewModelScope.launch {
            detailsUseCase.invoke(id = launchId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        updateState {
                            copy(
                                details = result.data.toUiModel(),
                                isLoading = false,
                                error = null,
                            )
                        }
                    }

                    is Result.Error -> {
                        val uiText = (result.exception as? DataErrorException)?.error?.toUiText()
                        updateState {
                            copy(
                                isLoading = false,
                                error = uiText,
                            )
                        }
                    }

                    Result.Loading -> {
                        updateState {
                            copy(
                                isLoading = true,
                            )
                        }
                    }
                }
            }
        }
    }
}
