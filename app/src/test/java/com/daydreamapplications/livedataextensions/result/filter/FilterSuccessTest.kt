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

class FilterSuccessTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `filterSuccess - value is Result Error - has no value`() {
        val value: Result.Error = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterSuccess.filterSuccess(source)
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterSuccess.filterSuccess(source)
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Success - value is data`() {
        val value: Result.Success<Unit> = mockk()
        every { value.data } returns Unit
        val source = liveDataOf<Result<Unit>>(value)

        FilterSuccess.filterSuccess(source)
            .assertValue(Unit)

        verifySequence {
            value.data
        }
    }
}