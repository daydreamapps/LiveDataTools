package com.daydreamapplications.livedataextensions.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.livedata.emptyLiveData
import com.daydreamapplications.livedataextensions.livedata.liveDataOf
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FilterResultTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(FilterResult)
    }

    @After
    fun tearDown() {
        unmockkObject(FilterResult)
    }

    // Object functionality tests

    @Test
    fun `filterError - value is Result Error - value is exception`() {
        val throwable: Throwable = mockk()
        val value: Result.Error = mockk {
            every { cause } returns throwable
        }
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterError(source)
            .assertValue(throwable)

        verifySequence {
            value.cause
        }
    }

    @Test
    fun `filterError - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterError(source)
            .assertNoValue()
    }

    @Test
    fun `filterError - value is Result Success - has no value`() {
        val value: Result.Success<Unit> = mockk()
        every { value.data } returns Unit
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterError(source)
            .assertNoValue()
    }

    @Test
    fun `filterLoading - value is Result Error - has no value`() {
        val value: Result.Error = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterLoading(source)
            .assertNoValue()
    }

    @Test
    fun `filterLoading - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterLoading(source)
            .assertValue(value)
    }

    @Test
    fun `filterLoading - value is Result Success - has no value`() {
        val value: Result.Success<Unit> = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterLoading(source)
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Error - has no value`() {
        val value: Result.Error = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterSuccess(source)
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterSuccess(source)
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Success - value is data`() {
        val value: Result.Success<Unit> = mockk()
        every { value.data } returns Unit
        val source =
            liveDataOf<Result<Unit>>(
                value
            )

        FilterResult.filterSuccess(source)
            .assertValue(Unit)

        verifySequence {
            value.data
        }
    }

    // Extension Function Tests

    @Test
    fun filterError() {
        val source =
            emptyLiveData<Result<Unit>>()

        every { FilterResult.filterError(source) } returns mockk()
        source.filterError()

        verify { FilterResult.filterError(source) }
    }

    @Test
    fun filterLoading() {
        val source =
            emptyLiveData<Result<Unit>>()

        every { FilterResult.filterLoading(source) } returns mockk()
        source.filterLoading()

        verify { FilterResult.filterLoading(source) }
    }

    @Test
    fun filterSuccess() {
        val source =
            emptyLiveData<Result<Unit>>()

        every { FilterResult.filterSuccess(source) } returns mockk()
        source.filterSuccess()

        verify { FilterResult.filterSuccess(source) }
    }

}
