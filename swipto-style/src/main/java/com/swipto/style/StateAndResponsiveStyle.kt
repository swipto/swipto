package com.swipto.style

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

enum class StyleState { Normal, Pressed, Focused, Disabled, Selected, Hovered }

/** State variants compose over [normal], retaining shared declarations. */
@Immutable
data class StatefulStyle(
    val normal: Style = Style.Empty,
    val pressed: Style? = null,
    val focused: Style? = null,
    val disabled: Style? = null,
    val selected: Style? = null,
    val hovered: Style? = null,
) {
    fun resolve(state: StyleState): Style = normal + when (state) {
        StyleState.Normal -> Style.Empty
        StyleState.Pressed -> pressed ?: Style.Empty
        StyleState.Focused -> focused ?: Style.Empty
        StyleState.Disabled -> disabled ?: Style.Empty
        StyleState.Selected -> selected ?: Style.Empty
        StyleState.Hovered -> hovered ?: Style.Empty
    }
}

class StatefulStyleBuilder {
    var normal: Style = Style.Empty
    var pressed: Style? = null
    var focused: Style? = null
    var disabled: Style? = null
    var selected: Style? = null
    var hovered: Style? = null
    fun normal(block: StyleBuilder.() -> Unit) { normal = style(block) }
    fun pressed(block: StyleBuilder.() -> Unit) { pressed = style(block) }
    fun focused(block: StyleBuilder.() -> Unit) { focused = style(block) }
    fun disabled(block: StyleBuilder.() -> Unit) { disabled = style(block) }
    fun selected(block: StyleBuilder.() -> Unit) { selected = style(block) }
    fun hovered(block: StyleBuilder.() -> Unit) { hovered = style(block) }
    fun build() = StatefulStyle(normal, pressed, focused, disabled, selected, hovered)
}
fun statefulStyle(block: StatefulStyleBuilder.() -> Unit): StatefulStyle = StatefulStyleBuilder().apply(block).build()

enum class WindowSize { Compact, Medium, Expanded }

@Immutable
data class ResponsiveStyle(val compact: Style = Style.Empty, val medium: Style? = null, val expanded: Style? = null) {
    fun resolve(size: WindowSize): Style = when (size) {
        WindowSize.Compact -> compact
        WindowSize.Medium -> medium ?: compact
        WindowSize.Expanded -> expanded ?: medium ?: compact
    }
}

class ResponsiveStyleBuilder {
    var compact: Style = Style.Empty
    var medium: Style? = null
    var expanded: Style? = null
    fun compact(block: StyleBuilder.() -> Unit) { compact = style(block) }
    fun medium(block: StyleBuilder.() -> Unit) { medium = style(block) }
    fun expanded(block: StyleBuilder.() -> Unit) { expanded = style(block) }
    fun build() = ResponsiveStyle(compact, medium, expanded)
}
fun responsiveStyle(block: ResponsiveStyleBuilder.() -> Unit): ResponsiveStyle = ResponsiveStyleBuilder().apply(block).build()

fun windowSizeFor(widthDp: Int): WindowSize = when {
    widthDp < 600 -> WindowSize.Compact
    widthDp < 840 -> WindowSize.Medium
    else -> WindowSize.Expanded
}

/** Centralized size-class lookup; apps do not need to inspect screen width themselves. */
@Composable
fun rememberWindowSize(configuration: Configuration = LocalConfiguration.current): WindowSize =
    remember(configuration.screenWidthDp) { windowSizeFor(configuration.screenWidthDp) }

@Composable
fun ResponsiveStyle.resolveCurrent(): Style = resolve(rememberWindowSize())
