package com.daydreamapplications.livedataextensions.result.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import com.daydreamapplications.livedataextensions.result.Result
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class FilterLoadingTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `filterLoading - value is Result Error - has no value`() {
        val value: Result.Error = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterLoading.filterLoading(source)
            .assertNoValue()
    }

    @Test
    fun `filterLoading - value is Result Loading - has no value`() {
        val value: Result.Loading = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterLoading.filterLoading(source)
            .assertValue(value)
    }

    @Test
    fun `filterLoading - value is Result Success - has no value`() {
        val value: Result.Success<Unit> = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        FilterLoading.filterLoading(source)
            .assertNoValue()
    }
}