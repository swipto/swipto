package com.swipto.style

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

/**
 * A reusable style whose semantic values resolve from [LocalThemeTokens] when it is applied.
 * Use this for colors that must follow light and dark themes without rebuilding the declaration.
 */
@Immutable
class ThemedStyle internal constructor(private val modifiers: List<ThemeStyleModifier>) {
    @Composable
    internal fun applyTo(modifier: Modifier): Modifier {
        val tokens = LocalThemeTokens.current
        return modifiers.fold(modifier) { current, styleModifier -> styleModifier.applyTo(current, tokens) }
    }

    operator fun plus(other: ThemedStyle): ThemedStyle = ThemedStyle(modifiers + other.modifiers)
}

@Immutable
fun interface ThemeStyleModifier { fun applyTo(modifier: Modifier, tokens: ThemeTokens): Modifier }

@Composable
fun Modifier.style(style: ThemedStyle?): Modifier = style?.applyTo(this) ?: this

fun themedStyle(block: ThemedStyleBuilder.() -> Unit): ThemedStyle = ThemedStyleBuilder().apply(block).build()

class ThemedStyleBuilder {
    private val modifiers = mutableListOf<ThemeStyleModifier>()
    fun visual(block: ThemedVisualStyleBuilder.() -> Unit) {
        modifiers += ThemedVisualStyleBuilder().apply(block).build()
    }
    internal fun build(): ThemedStyle = ThemedStyle(modifiers.toList())
}

/** Theme-aware visual primitive, intentionally limited to semantic token-backed values. */
class ThemedVisualStyleBuilder {
    private val modifiers = mutableListOf<ThemeStyleModifier>()
    fun background(color: ColorToken) {
        modifiers += ThemeStyleModifier { modifier, tokens -> modifier.background(tokens.color(color)) }
    }
    internal fun build(): List<ThemeStyleModifier> = modifiers.toList()
}
