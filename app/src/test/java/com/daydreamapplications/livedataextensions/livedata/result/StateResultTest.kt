package com.daydreamapplications.livedataextensions.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.livedata.emptyLiveData
import com.daydreamapplications.livedataextensions.livedata.liveDataOf
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StateResultTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(StateResult)
    }

    @After
    fun tearDown() {
        unmockkObject(StateResult)
    }

    // Object functionality tests

    @Test
    fun `isError1 - value is Result Error - value is true`() {
        val value: Result.Error = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isError1(source)
            .assertValue(true)
    }

    @Test
    fun `isError1 - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isError1(source)
            .assertValue(false)
    }

    @Test
    fun `isError1 - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isError1(source)
            .assertValue(false)
    }

    @Test
    fun `isLoading1 - value is Result Error - value is false`() {
        val value: Result.Error = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isLoading1(source)
            .assertValue(false)
    }

    @Test
    fun `isLoading1 - value is Result Loading - value is true`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isLoading1(source)
            .assertValue(true)
    }

    @Test
    fun `isLoading1 - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isLoading1(source)
            .assertValue(false)
    }

    @Test
    fun `isSuccess1 - value is Result Error - value is false`() {
        val value: Result.Error = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isSuccess1(source)
            .assertValue(false)
    }

    @Test
    fun `isSuccess1 - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isSuccess1(source)
            .assertValue(false)
    }

    @Test
    fun `isSuccess1 - value is Result Success - value is true`() {
        val value: Result.Success<Unit> = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        StateResult.isSuccess1(source)
            .assertValue(true)
    }

    // Extension Function Tests

    @Test
    fun `isError extension function - calls StateResult isError1`() {
        val source =
            emptyLiveData<Result<Unit>>()
        every { StateResult.isError1(source) } returns mockk()

        source.isError()

        verify { StateResult.isError1(source) }
    }

    @Test
    fun `isLoading extension function - calls StateResult isLoading1`() {
        val source =
            emptyLiveData<Result<Unit>>()
        every { StateResult.isLoading1(source) } returns mockk()

        source.isLoading()

        verify { StateResult.isLoading1(source) }
    }

    @Test
    fun `isSuccess extension function - calls StateResult isSuccess1`() {
        val source =
            emptyLiveData<Result<Unit>>()
        every { StateResult.isSuccess1(source) } returns mockk()

        source.isSuccess()

        verify { StateResult.isSuccess1(source) }
    }
}
