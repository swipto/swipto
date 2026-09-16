package com.swipto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.swipto.style.Elevation
import com.swipto.style.LocalThemeTokens
import com.swipto.style.Radius
import com.swipto.style.Spacing
import com.swipto.style.ThemeTokens

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
    spacing: Spacing = Spacing(),
    radius: Radius = Radius(),
    elevation: Elevation = Elevation(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val typography = androidx.compose.material3.Typography()
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
    ) {
        CompositionLocalProvider(
            LocalThemeTokens provides ThemeTokens(colors, typography, spacing, radius, elevation),
            content = content,
        )
    }
}
