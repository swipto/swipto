package com.swipto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0B6E4F),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF1B9AAA),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF7F9F8),
    onBackground = Color(0xFF102A23),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF102A23),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF3DDBA3),
    onPrimary = Color(0xFF003825),
    secondary = Color(0xFF5ED0DC),
    onSecondary = Color(0xFF00363C),
    background = Color(0xFF0B1411),
    onBackground = Color(0xFFE6F2ED),
    surface = Color(0xFF12201B),
    onSurface = Color(0xFFE6F2ED),
)

@Composable
fun SwiptoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
