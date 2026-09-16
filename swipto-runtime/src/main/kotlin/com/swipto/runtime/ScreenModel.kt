package com.swipto.runtime

import com.swipto.core.Outcome
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Marker for immutable state rendered by a screen. */
interface UiState

/** A standard representation for data that is loaded asynchronously. */
sealed interface LoadState<out T> {
    data object Initial : LoadState<Nothing>
    data object Loading : LoadState<Nothing>
    data class Content<T>(val value: T) : LoadState<T>
    data object Empty : LoadState<Nothing>
    data class Failure(val error: Throwable) : LoadState<Nothing>
}

/** One-time UI work, such as navigation or showing a message. */
interface UiEffect

/**
 * Platform-independent state holder. Android ViewModels may delegate to this class,
 * while unit tests can exercise state transitions without Android dependencies.
 */
open class StateStore<S : UiState, E : UiEffect>(initialState: S) {
    private val mutableState = MutableStateFlow(initialState)
    private val mutableEffects = MutableSharedFlow<E>(extraBufferCapacity = 1)

    val state: StateFlow<S> = mutableState.asStateFlow()
    val effects: SharedFlow<E> = mutableEffects.asSharedFlow()

    protected fun update(reducer: (S) -> S) = mutableState.update(reducer)
    protected fun setState(state: S) { mutableState.value = state }
    protected fun emit(effect: E) { mutableEffects.tryEmit(effect) }
}

/** Converts the established Swipto outcome type into a standard screen load state. */
fun <T> Outcome<T>.toLoadState(empty: (T) -> Boolean = { false }): LoadState<T> = when (this) {
    is Outcome.Success -> if (empty(value)) LoadState.Empty else LoadState.Content(value)
    is Outcome.Failure -> LoadState.Failure(error)
}
