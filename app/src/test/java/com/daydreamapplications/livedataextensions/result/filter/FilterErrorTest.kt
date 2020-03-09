package com.daydreamapplications.livedataextensions.result.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import com.daydreamapplications.livedataextensions.result.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.Test

class FilterErrorTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `filterError - value is Result Error - value is exception`() {
        val throwable: Throwable = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }
        val source = liveDataOf<Result<Unit>>(value)

        FilterError.filterError(source)
            .assertValue(throwable)

        verifySequence {
            value.exception
        }
    }

    @Test
    fun `filterError - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterError.filterError(source)
            .assertNoValue()
    }

    @Test
    fun `filterError - value is Result Success - has no value`() {
        val value: Result.Success<Unit> = mockk()
        every { value.data } returns Unit
        val source = liveDataOf<Result<Unit>>(value)

        FilterError.filterError(source)
            .assertNoValue()
    }
}