package com.swipto.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

/**
 * Base activity for Compose hosts. Enables edge-to-edge and calls [Content].
 */
abstract class SwiptoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Content()
    }

    /** Set Compose content / navigation host here. */
    protected abstract fun Content()
}
