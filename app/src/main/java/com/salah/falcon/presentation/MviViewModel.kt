package com.salah.falcon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<State : Any, Action : MviAction, SideEffect : MviSideEffect> : ViewModel() {

    private val sideEffectChannel = Channel<SideEffect>(capacity = Channel.BUFFERED)
    val sideEffect: Flow<SideEffect>
        get() = sideEffectChannel.receiveAsFlow()


    protected open val uiStateKey: String = this.javaClass.name
    private val initialUiState: State by lazy {
         createDefaultUiState()
    }
    private val _stateFlow = MutableStateFlow(initialUiState)
    val uiStateFlow: StateFlow<State> = _stateFlow.asStateFlow()

    abstract fun createDefaultUiState(): State

    protected fun updateState(updater: State.() -> State) {
        _stateFlow.update {
            val newState = updater.invoke(it)
            newState
        }
    }

    abstract fun handleAction(action: Action)

    protected fun sendSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            sideEffectChannel.send(sideEffect)
        }
    }
}
