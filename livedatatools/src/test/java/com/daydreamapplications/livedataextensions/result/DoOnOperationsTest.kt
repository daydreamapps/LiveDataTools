package com.daydreamapplications.livedatatools.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.emptyLiveData
import com.daydreamapplications.livedatatools.liveDataOf
import com.daydreamapplications.livedatatools.test
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class DoOnOperationsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Object functionality tests

    @Test
    fun `doOnNextResult - liveData has value - no argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()

        val source = emptyLiveData<Result<Unit>>()
        DoOnOperations.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verify {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has null value - no argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()

        val source = liveDataOf<Result<Unit>>(null)
        DoOnOperations.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verify {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has Error value - onError argument is invoked`() {
        val throwable: Throwable = mockk()
        val onError: (Throwable) -> Unit = mockk()
        every { onError(throwable) } just Runs
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verifySequence {
            value.exception
            onError(throwable)
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has Loading value - onLoading argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        every { onLoading() } just Runs
        val value: Result.Loading = mockk()

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verifySequence {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading()
        }
    }

    @Test
    fun `doOnNextResult - liveData has Success value - onSuccess argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        every { onSuccess(Unit) } just Runs
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verifySequence {
            value.data
            onError wasNot Called
            onSuccess(Unit)
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnError - liveData has Error value - onError is invoked with exception`() {
        val throwable: Throwable = mockk()
        val onError: (Throwable) -> Unit = mockk()
        every { onError(throwable) } just Runs
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        liveDataOf<Result<Unit>>(value)
            .doOnError(onError)
            .test()

        verifySequence {
            value.exception
            onError(throwable)
        }
    }

    @Test
    fun `doOnError - liveData has Loading value - onError is not invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val value: Result.Loading = mockk()

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnError(source, onError)
            .test()

        verify {
            onError wasNot Called
        }
    }

    @Test
    fun `doOnError - liveData has Success value - onError is not invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnError(source, onError)
            .test()

        verify {
            onError wasNot Called
        }
    }

    @Test
    fun `doOnLoading - liveData has Error value - onLoading is not invoked`() {
        val throwable: Throwable = mockk()
        val onLoading: () -> Unit = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnLoading(source, onLoading)
            .test()

        verify {
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnLoading - liveData has Loading value - onLoading is invoked`() {
        val onLoading: () -> Unit = mockk()
        val value: Result.Loading = mockk()
        every { onLoading() } just Runs

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnLoading(source, onLoading)
            .test()

        verifySequence {
            onLoading()
        }
    }

    @Test
    fun `doOnLoading - liveData has Success value - onLoading is not invoked`() {
        val onLoading: () -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnLoading(source, onLoading)
            .test()

        verify {
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Error value - onSuccess is not invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Error = mockk()

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnSuccess(source, onSuccess)
            .test()

        verify {
            onSuccess wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Loading value - onSuccess is invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Loading = mockk()

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnSuccess(source, onSuccess)
            .test()

        verify {
            onSuccess wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Success value - onSuccess is not invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        every { onSuccess(Unit) } just Runs

        val source = liveDataOf<Result<Unit>>(value)
        DoOnOperations.doOnSuccess(source, onSuccess)
            .test()

        verifySequence {
            value.data
            onSuccess(Unit)
        }
    }

    // Extension Function Tests

    @Test
    fun `doOnNextResult extension function - calls DoOnOperations doOnNextResult`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onError: ((Throwable) -> Unit)? = mockk()
        val onSuccess: ((Unit) -> Unit)? = mockk()
        val onLoading: (() -> Unit)? = mockk()

        mockkObject(DoOnOperations)
        every {
            DoOnOperations.doOnNextResult(
                source = source,
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            )
        } returns mockk()

        source.doOnNextResult(
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        )

        verify {
            DoOnOperations.doOnNextResult(
                source = source,
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            )
        }
    }

    @Test
    fun `doOnError extension function - calls DoOnOperations doOnError`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onError: (Throwable) -> Unit = mockk()

        mockkObject(DoOnOperations)
        every {
            DoOnOperations.doOnError(
                source = source,
                onError = onError
            )
        } returns mockk()

        source.doOnError(onError = onError)

        verify {
            DoOnOperations.doOnError(
                source = source,
                onError = onError
            )
        }
    }

    @Test
    fun `doOnLoading extension function - calls DoOnOperations doOnLoading`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onLoading: () -> Unit = mockk()

        mockkObject(DoOnOperations)
        every {
            DoOnOperations.doOnLoading(
                source = source,
                onLoading = onLoading
            )
        } returns mockk()

        source.doOnLoading(onLoading = onLoading)

        verify {
            DoOnOperations.doOnLoading(
                source = source,
                onLoading = onLoading
            )
        }
    }

    @Test
    fun `doOnSuccess extension function - calls DoOnOperations doOnSuccess`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onSuccess: (Unit) -> Unit = mockk()

        mockkObject(DoOnOperations)
        every {
            DoOnOperations.doOnSuccess(
                source = source,
                onSuccess = onSuccess
            )
        } returns mockk()

        source.doOnSuccess(onSuccess = onSuccess)

        verify {
            DoOnOperations.doOnSuccess(
                source = source,
                onSuccess = onSuccess
            )
        }
    }
}