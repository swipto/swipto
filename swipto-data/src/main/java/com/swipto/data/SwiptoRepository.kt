package com.swipto.data

import com.swipto.core.Outcome
import kotlinx.coroutines.CancellationException

/**
 * Marker for repositories owned by the data layer.
 */
interface SwiptoRepository

/**
 * Helper for mapping throwing calls into [Outcome].
 */
suspend fun <T> repositoryCall(block: suspend () -> T): Outcome<T> =
    try {
        Outcome.Success(block())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (t: Throwable) {
        Outcome.Failure(t)
    }
