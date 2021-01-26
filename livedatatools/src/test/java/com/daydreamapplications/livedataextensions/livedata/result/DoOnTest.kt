package com.daydreamapplications.livedatatools.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.livedata.emptyLiveData
import com.daydreamapplications.livedatatools.livedata.liveDataOf
import com.daydreamapplications.livedatatools.test
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DoOnTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(DoOn)
    }

    @After
    fun tearDown() {
        unmockkObject(DoOn)
    }

    // Object functionality tests

    @Test
    fun `doOnNextResult - liveData has value - no argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()

        val source =
            emptyLiveData<Result<Unit>>()
        DoOn.doOnNextResult(
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

        val source =
            liveDataOf<Result<Unit>>(
                null
            )
        DoOn.doOnNextResult(
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
            every { cause } returns throwable
        }

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = onSuccess,
            onLoading = onLoading
        ).test()

        verifySequence {
            value.cause
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnNextResult(
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnNextResult(
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
    fun `doOnError - liveData has Error value - onError is invoked with cause`() {
        val throwable: Throwable = mockk()
        val onError: (Throwable) -> Unit = mockk()
        every { onError(throwable) } just Runs
        val value: Result.Error = mockk {
            every { cause } returns throwable
        }

        liveDataOf<Result<Unit>>(
            value
        )
            .doOnError(onError)
            .test()

        verifySequence {
            value.cause
            onError(throwable)
        }
    }

    @Test
    fun `doOnError - liveData has Loading value - onError is not invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val value: Result.Loading = mockk()

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnError(source, onError)
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnError(source, onError)
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
            every { cause } returns throwable
        }

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnLoading(source, onLoading)
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnLoading(source, onLoading)
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnLoading(source, onLoading)
            .test()

        verify {
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Error value - onSuccess is not invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Error = mockk()

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnSuccess(source, onSuccess)
            .test()

        verify {
            onSuccess wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Loading value - onSuccess is invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Loading = mockk()

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnSuccess(source, onSuccess)
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

        val source =
            liveDataOf<Result<Unit>>(
                value
            )
        DoOn.doOnSuccess(source, onSuccess)
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

        every {
            DoOn.doOnNextResult(
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
            DoOn.doOnNextResult(
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

        every {
            DoOn.doOnError(
                source = source,
                onError = onError
            )
        } returns mockk()

        source.doOnError(onError = onError)

        verify {
            DoOn.doOnError(
                source = source,
                onError = onError
            )
        }
    }

    @Test
    fun `doOnLoading extension function - calls DoOnOperations doOnLoading`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onLoading: () -> Unit = mockk()

        every {
            DoOn.doOnLoading(
                source = source,
                onLoading = onLoading
            )
        } returns mockk()

        source.doOnLoading(onLoading = onLoading)

        verify {
            DoOn.doOnLoading(
                source = source,
                onLoading = onLoading
            )
        }
    }

    @Test
    fun `doOnSuccess extension function - calls DoOnOperations doOnSuccess`() {
        val source: LiveData<Result<Unit>> = mockk()
        val onSuccess: (Unit) -> Unit = mockk()

        every {
            DoOn.doOnSuccess(
                source = source,
                onSuccess = onSuccess
            )
        } returns mockk()

        source.doOnSuccess(onSuccess = onSuccess)

        verify {
            DoOn.doOnSuccess(
                source = source,
                onSuccess = onSuccess
            )
        }
    }
}