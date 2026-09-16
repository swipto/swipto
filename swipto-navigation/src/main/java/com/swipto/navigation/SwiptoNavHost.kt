package com.swipto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Registers destinations for a Swipto nav graph.
 */
class SwiptoNavGraphBuilder {
    internal val destinations = mutableListOf<SwiptoDestination>()

    fun destination(route: SwiptoRoute, content: @Composable () -> Unit) {
        destinations += SwiptoDestination(route, content)
    }
}

internal data class SwiptoDestination(
    val route: SwiptoRoute,
    val content: @Composable () -> Unit,
)

fun SwiptoNavGraph(block: SwiptoNavGraphBuilder.() -> Unit): SwiptoNavGraphBuilder =
    SwiptoNavGraphBuilder().apply(block)

@Composable
fun SwiptoNavHost(
    start: SwiptoRoute,
    graph: SwiptoNavGraphBuilder,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = start.route,
        modifier = modifier,
    ) {
        graph.destinations.forEach { dest ->
            composable(dest.route.route) {
                dest.content()
            }
        }
    }
}

fun NavGraphBuilder.swiptoComposable(
    route: SwiptoRoute,
    content: @Composable () -> Unit,
) {
    composable(route.route) { content() }
}
