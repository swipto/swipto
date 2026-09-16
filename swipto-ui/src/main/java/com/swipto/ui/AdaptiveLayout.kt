package com.swipto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

/** Semantic width classes for responsive Compose layouts. */
enum class AdaptiveWidth { Compact, Medium, Expanded }

@Composable
fun rememberAdaptiveWidth(): AdaptiveWidth {
    val width = LocalConfiguration.current.screenWidthDp
    return remember(width) {
        when {
            width < 600 -> AdaptiveWidth.Compact
            width < 840 -> AdaptiveWidth.Medium
            else -> AdaptiveWidth.Expanded
        }
    }
}

@Composable
fun AdaptiveLayout(
    compact: @Composable () -> Unit,
    medium: @Composable () -> Unit = compact,
    expanded: @Composable () -> Unit = medium,
) = when (rememberAdaptiveWidth()) {
    AdaptiveWidth.Compact -> compact()
    AdaptiveWidth.Medium -> medium()
    AdaptiveWidth.Expanded -> expanded()
}
