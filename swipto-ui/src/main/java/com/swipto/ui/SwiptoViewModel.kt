package com.swipto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Opinionated ViewModel with a single immutable [UiState] exposed as [StateFlow].
 */
abstract class SwiptoViewModel<UiState>(initialState: UiState) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<UiState> = _state.asStateFlow()

    protected val uiState: UiState get() = _state.value

    protected fun update(reducer: (UiState) -> UiState) {
        _state.update(reducer)
    }

    protected fun setState(value: UiState) {
        _state.value = value
    }

    /** Launch work in [viewModelScope]. */
    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}
