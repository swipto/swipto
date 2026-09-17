package com.swipto.data

import com.swipto.core.Outcome
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertSame
import org.junit.Assert.fail
import org.junit.Test

class RepositoryCallTest {
    @Test
    fun `repository call propagates coroutine cancellation`() = runBlocking {
        val cancellation = CancellationException("screen left")

        try {
            repositoryCall<String> { throw cancellation }
            fail("Cancellation must not be represented as Outcome.Failure")
        } catch (actual: CancellationException) {
            assertSame(cancellation, actual)
        }
    }

    @Test
    fun `repository call maps ordinary failure to outcome`() = runBlocking {
        val result = repositoryCall<String> { error("network unavailable") }

        check(result is Outcome.Failure)
    }
}
