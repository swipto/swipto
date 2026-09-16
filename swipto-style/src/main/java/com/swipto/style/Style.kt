package com.swipto.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Immutable Kotlin styling declaration applied explicitly to a Compose [Modifier]. */
@Immutable
class Style internal constructor(private val transform: Modifier.() -> Modifier) {
    internal fun applyTo(modifier: Modifier): Modifier = modifier.transform()
    operator fun plus(other: Style) = Style { this@Style.applyTo(other.applyTo(this)) }
}

fun Modifier.style(style: Style?): Modifier = style?.applyTo(this) ?: this

class StyleBuilder {
    private var transform: Modifier.() -> Modifier = { this }

    fun fillMaxSize() = append { fillMaxSize() }
    fun padding(all: Dp) = append { padding(all) }
    fun padding(values: PaddingValues) = append { padding(values) }

    private fun append(next: Modifier.() -> Modifier) {
        val previous = transform
        transform = { next(previous(this)) }
    }

    internal fun build() = Style(transform)
}

fun style(block: StyleBuilder.() -> Unit): Style = StyleBuilder().apply(block).build()

/** Shared dimensions used by styles and components. */
@Immutable
data class Spacing(val xs: Dp = 4.dp, val sm: Dp = 8.dp, val md: Dp = 16.dp, val lg: Dp = 24.dp)
