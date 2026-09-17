package com.swipto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.swipto.runtime.LoadState

/** Renders standard async states without requiring each screen state to carry loading/error fields. */
@Composable
fun <T> AsyncContent(
    state: LoadState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    empty: @Composable () -> Unit = { Text("Nothing to show") },
    content: @Composable (T) -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            LoadState.Initial, LoadState.Loading -> androidx.compose.material3.CircularProgressIndicator()
            is LoadState.Content -> content(state.value)
            LoadState.Empty -> empty()
            is LoadState.Failure -> ErrorContent(state.error.message ?: "Something went wrong", onRetry)
        }
    }
}

@Composable
fun ErrorContent(message: String, onRetry: (() -> Unit)? = null) {
    Text(text = if (onRetry == null) message else "$message. Try again.")
}

/** Lifecycle-aware screen content for a [StateFlow] owned by a runtime state store or ViewModel. */
@Composable
fun <S> Screen(
    state: kotlinx.coroutines.flow.StateFlow<S>,
    content: @Composable (S) -> Unit,
) {
    val value by androidx.lifecycle.compose.collectAsStateWithLifecycle(state)
    content(value)
}
