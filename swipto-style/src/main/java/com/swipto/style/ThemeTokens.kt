package com.swipto.style

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable data class Spacing(val xs: Dp = 4.dp, val sm: Dp = 8.dp, val md: Dp = 16.dp, val lg: Dp = 24.dp, val xl: Dp = 32.dp)
@Immutable data class Radius(val small: Dp = 4.dp, val medium: Dp = 8.dp, val large: Dp = 16.dp)
@Immutable data class Elevation(val none: Dp = 0.dp, val low: Dp = 2.dp, val medium: Dp = 6.dp, val high: Dp = 12.dp)
@Immutable data class ThemeTokens(val colors: ColorScheme, val typography: Typography, val spacing: Spacing = Spacing(), val radius: Radius = Radius(), val elevation: Elevation = Elevation())

val LocalThemeTokens = staticCompositionLocalOf<ThemeTokens> {
    error("ThemeTokens are unavailable. Wrap content in SwiptoTheme or provide LocalThemeTokens.")
}

/** Semantic color reference that resolves against the active light/dark [ThemeTokens]. */
enum class ColorToken { Primary, OnPrimary, Secondary, Background, OnBackground, Surface, OnSurface, Error }
fun ThemeTokens.color(token: ColorToken): Color = when (token) {
    ColorToken.Primary -> colors.primary; ColorToken.OnPrimary -> colors.onPrimary
    ColorToken.Secondary -> colors.secondary; ColorToken.Background -> colors.background
    ColorToken.OnBackground -> colors.onBackground; ColorToken.Surface -> colors.surface
    ColorToken.OnSurface -> colors.onSurface; ColorToken.Error -> colors.error
}
