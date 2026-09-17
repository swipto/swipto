package com.swipto.ui

import androidx.compose.runtime.Composable
import com.swipto.ui.theme.SwiptoTheme

/** Root Compose host for a Swipto application. */
@Composable
fun SwiptoApp(content: @Composable () -> Unit) {
    SwiptoTheme(content = content)
}
