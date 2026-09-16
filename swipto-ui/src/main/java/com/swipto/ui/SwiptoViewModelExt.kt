package com.swipto.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Resolve a [ViewModel] from Koin inside Compose.
 */
@Composable
inline fun <reified T : ViewModel> swiptoViewModel(): T = koinViewModel()
