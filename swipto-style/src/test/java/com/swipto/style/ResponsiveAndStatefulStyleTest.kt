package com.swipto.style

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ResponsiveAndStatefulStyleTest {
    @Test
    fun `window sizes use centralized material breakpoints`() {
        assertEquals(WindowSize.Compact, windowSizeFor(599))
        assertEquals(WindowSize.Medium, windowSizeFor(600))
        assertEquals(WindowSize.Medium, windowSizeFor(839))
        assertEquals(WindowSize.Expanded, windowSizeFor(840))
    }

    @Test
    fun `responsive styles fall back to the next smaller declaration`() {
        val compact = style { padding(4.dp) }
        val responsive = ResponsiveStyle(compact = compact)

        assertSame(compact, responsive.resolve(WindowSize.Medium))
        assertSame(compact, responsive.resolve(WindowSize.Expanded))
    }

    @Test
    fun `state variants retain normal declarations and append overrides`() {
        val normal = style { padding(4.dp) }
        val pressed = style { padding(8.dp) }
        val resolved = StatefulStyle(normal = normal, pressed = pressed).resolve(StyleState.Pressed)

        assertEquals(normal.modifiers.size + pressed.modifiers.size, resolved.modifiers.size)
    }
}
