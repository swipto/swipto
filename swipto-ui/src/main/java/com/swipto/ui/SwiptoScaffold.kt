package com.swipto.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Level 1 application chrome with a consistent Material 3 top bar.
 * Pass [topBar] for a custom bar, or use Material [Scaffold] directly for full control.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwiptoScaffold(
    title: String? = null,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {
        title?.let { titleText ->
            CenterAlignedTopAppBar(title = { Text(titleText) })
        }
    },
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        content = content,
    )
}
