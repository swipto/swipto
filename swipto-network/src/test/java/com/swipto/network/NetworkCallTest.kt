package com.swipto.network

import com.swipto.core.Outcome
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertSame
import org.junit.Assert.fail
import org.junit.Test

class NetworkCallTest {
    @Test
    fun `network call propagates coroutine cancellation`() = runBlocking {
        val cancellation = CancellationException("screen left")

        try {
            networkCall<String> { throw cancellation }
            fail("Cancellation must not be represented as Outcome.Failure")
        } catch (actual: CancellationException) {
            assertSame(cancellation, actual)
        }
    }

    @Test
    fun `network call maps ordinary failure to outcome`() = runBlocking {
        val result = networkCall<String> { error("unavailable") }

        check(result is Outcome.Failure)
    }
}
