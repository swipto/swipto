package com.swipto.style

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/** A reusable, immutable sequence of native Compose modifier operations. */
@Immutable
class Style internal constructor(internal val modifiers: List<StyleModifier>) {
    internal fun applyTo(modifier: Modifier): Modifier =
        modifiers.fold(modifier) { current, styleModifier -> styleModifier.applyTo(current) }

    /** Appends [other], so its modifiers are applied after this style's modifiers. */
    operator fun plus(other: Style): Style = Style(modifiers + other.modifiers)

    companion object { val Empty: Style = Style(emptyList()) }
}

/** A focused styling primitive; implementations map directly to Compose APIs. */
@Immutable
fun interface StyleModifier { fun applyTo(modifier: Modifier): Modifier }

/** Applies a style while retaining normal [Modifier] composition as an escape hatch. */
fun Modifier.style(style: Style?): Modifier = style?.applyTo(this) ?: this

/** Builds a style from focused layout, visual, and typography primitives. */
fun style(block: StyleBuilder.() -> Unit): Style = StyleBuilder().apply(block).build()

class StyleBuilder {
    private val modifiers = mutableListOf<StyleModifier>()

    fun layout(block: LayoutStyleBuilder.() -> Unit) {
        modifiers += LayoutStyleBuilder().apply(block).build()
    }

    fun visual(block: VisualStyleBuilder.() -> Unit) {
        modifiers += VisualStyleBuilder().apply(block).build()
    }

    /** Metadata for text-bearing components; retrieve it with [Style.textStyleOrNull]. */
    fun typography(block: TypographyStyleBuilder.() -> Unit) {
        modifiers += TypographyStyleBuilder().apply(block).build()
    }

    /** Compatibility conveniences for the original compact API. */
    fun fillMaxSize() = layout { fillWidth(); fillHeight() }
    fun padding(all: Dp) = layout { padding(all) }
    fun padding(values: PaddingValues) = layout { padding(values) }

    internal fun add(modifier: StyleModifier) { modifiers += modifier }
    internal fun build(): Style = Style(modifiers.toList())
}

@Immutable
class LayoutStyle internal constructor(
    val alignment: Alignment? = null,
    val spacing: Dp? = null,
)

class LayoutStyleBuilder {
    var alignment: Alignment? = null
    var spacing: Dp? = null
    private val modifiers = mutableListOf<StyleModifier>()

    fun padding(all: Dp) = add { it.padding(all) }
    fun padding(values: PaddingValues) = add { it.padding(values) }
    fun padding(horizontal: Dp, vertical: Dp) = add { it.padding(horizontal = horizontal, vertical = vertical) }
    /** Compose has no margin modifier; this adds outer space before the styled content. */
    fun margin(all: Dp) = add { it.padding(all) }
    fun width(value: Dp) = add { it.width(value) }
    fun height(value: Dp) = add { it.height(value) }
    fun minWidth(value: Dp) = add { it.defaultMinSize(minWidth = value) }
    fun minHeight(value: Dp) = add { it.defaultMinSize(minHeight = value) }
    fun sizeIn(minWidth: Dp = Dp.Unspecified, maxWidth: Dp = Dp.Unspecified, minHeight: Dp = Dp.Unspecified, maxHeight: Dp = Dp.Unspecified) =
        add { it.sizeIn(minWidth, maxWidth, minHeight, maxHeight) }
    fun fillWidth(fraction: Float = 1f) = add { it.fillMaxWidth(fraction) }
    fun fillHeight(fraction: Float = 1f) = add { it.fillMaxHeight(fraction) }
    fun aspectRatio(ratio: Float, matchHeightConstraintsFirst: Boolean = false) = add { it.aspectRatio(ratio, matchHeightConstraintsFirst) }

    internal fun build(): List<StyleModifier> = modifiers.toList() + LayoutMetadata(LayoutStyle(alignment, spacing))
    private fun add(operation: Modifier.() -> Modifier) { modifiers += StyleModifier { operation(it) } }
}

@Immutable
internal data class LayoutMetadata(val layout: LayoutStyle) : StyleModifier {
    override fun applyTo(modifier: Modifier): Modifier = modifier
}

/** Layout metadata for containers that can consume it, such as [AppColumn][com.swipto.components.AppColumn]. */
fun Style.layoutStyleOrNull(): LayoutStyle? = modifiers.filterIsInstance<LayoutMetadata>().lastOrNull()?.layout

class VisualStyleBuilder {
    private val modifiers = mutableListOf<StyleModifier>()

    fun background(color: Color, shape: Shape? = null) = add { if (shape == null) it.background(color) else it.background(color, shape) }
    fun background(brush: Brush, shape: Shape? = null) = add { if (shape == null) it.background(brush) else it.background(brush, shape) }
    fun backgroundGradient(brush: Brush, shape: Shape? = null) = background(brush, shape)
    fun border(width: Dp, color: Color, shape: Shape = RoundedCornerShape(0)) = add { it.border(width, color, shape) }
    fun radius(value: Dp) = add { it.clip(RoundedCornerShape(value)) }
    fun opacity(value: Float) = add { it.alpha(value) }
    fun shadow(elevation: Dp, shape: Shape = RoundedCornerShape(0), clip: Boolean = false) = add { it.shadow(elevation, shape, clip) }
    fun elevation(value: Dp, shape: Shape = RoundedCornerShape(0)) = shadow(value, shape)

    internal fun build(): List<StyleModifier> = modifiers.toList()
    private fun add(operation: Modifier.() -> Modifier) { modifiers += StyleModifier { operation(it) } }
}

@Immutable
data class TypographyStyle(val value: TextStyle)

class TypographyStyleBuilder {
    var fontSize = TextStyle.Default.fontSize
    var fontWeight = TextStyle.Default.fontWeight
    var lineHeight = TextStyle.Default.lineHeight
    var letterSpacing = TextStyle.Default.letterSpacing
    var textAlign = TextStyle.Default.textAlign

    internal fun build(): TypographyMetadata = TypographyMetadata(
        TypographyStyle(
            TextStyle(
                fontSize = fontSize,
                fontWeight = fontWeight,
                lineHeight = lineHeight,
                letterSpacing = letterSpacing,
                textAlign = textAlign,
            ),
        ),
    )
}

@Immutable
internal data class TypographyMetadata(val style: TypographyStyle) : StyleModifier {
    override fun applyTo(modifier: Modifier): Modifier = modifier
}

fun Style.textStyleOrNull(): TextStyle? = modifiers.filterIsInstance<TypographyMetadata>().lastOrNull()?.style?.value
