package com.swipto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

/**
 * Optional loading/error contract for screen state.
 */
interface SwiptoUiState {
    val isLoading: Boolean get() = false
    val errorMessage: String? get() = null
}

/**
 * Collects [state] and renders loading, error, or [content].
 */
@Composable
fun <S> SwiptoScreen(
    state: StateFlow<S>,
    modifier: Modifier = Modifier,
    content: @Composable (S) -> Unit,
) {
    val current by state.collectAsStateWithLifecycle()
    SwiptoScreen(state = current, modifier = modifier, content = content)
}

@Composable
fun <S> SwiptoScreen(
    state: S,
    modifier: Modifier = Modifier,
    content: @Composable (S) -> Unit,
) {
    val loading = (state as? SwiptoUiState)?.isLoading == true
    val error = (state as? SwiptoUiState)?.errorMessage

    Box(modifier = modifier.fillMaxSize()) {
        content(state)

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }

        if (!error.isNullOrBlank()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            )
        }
    }
}
