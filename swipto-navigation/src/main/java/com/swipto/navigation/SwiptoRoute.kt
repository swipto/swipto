package com.swipto.navigation

/**
 * Marker for a typed navigation destination.
 * [route] must be unique within a [SwiptoNavGraph].
 */
interface SwiptoRoute {
    val route: String
}

/** A route with typed arguments represented by its own Kotlin type. */
interface TypedSwiptoRoute : SwiptoRoute

/**
 * Simple route with a static path string.
 */
open class SwiptoSimpleRoute(override val route: String) : SwiptoRoute
