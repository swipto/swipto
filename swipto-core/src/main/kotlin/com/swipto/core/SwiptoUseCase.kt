package com.swipto.core

/**
 * Base use-case contract. Implementations encapsulate a single business action.
 */
fun interface SwiptoUseCase<in P, out R> {
    suspend operator fun invoke(params: P): Outcome<R>
}

/**
 * Use-case with no input parameters.
 */
fun interface SwiptoUseCaseNoParams<out R> {
    suspend operator fun invoke(): Outcome<R>
}
