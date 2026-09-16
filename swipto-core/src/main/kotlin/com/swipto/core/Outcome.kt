package com.swipto.core

/**
 * Lightweight success/failure wrapper for domain and data operations.
 */
sealed class Outcome<out T> {
    data class Success<T>(val value: T) : Outcome<T>()
    data class Failure(val error: Throwable) : Outcome<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? = (this as? Success)?.value

    fun exceptionOrNull(): Throwable? = (this as? Failure)?.error

    inline fun <R> map(transform: (T) -> R): Outcome<R> = when (this) {
        is Success -> Success(transform(value))
        is Failure -> this
    }

    inline fun <R> flatMap(transform: (T) -> Outcome<R>): Outcome<R> = when (this) {
        is Success -> transform(value)
        is Failure -> this
    }

    inline fun onSuccess(action: (T) -> Unit): Outcome<T> {
        if (this is Success) action(value)
        return this
    }

    inline fun onFailure(action: (Throwable) -> Unit): Outcome<T> {
        if (this is Failure) action(error)
        return this
    }

    companion object {
        inline fun <T> runCatching(block: () -> T): Outcome<T> =
            try {
                Success(block())
            } catch (t: Throwable) {
                Failure(t)
            }
    }
}
