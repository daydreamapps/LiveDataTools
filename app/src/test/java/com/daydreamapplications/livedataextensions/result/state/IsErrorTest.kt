package com.daydreamapplications.livedataextensions.result.state

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import com.daydreamapplications.livedataextensions.result.Result
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class IsErrorTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `isError - value is Result Error - value is true`() {
        val value: Result.Error = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        IsError.isError(source)
            .assertValue(true)
    }

    @Test
    fun `isError - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        IsError.isError(source)
            .assertValue(false)
    }

    @Test
    fun `isError - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()
        val source = liveDataOf<Result<Unit>>(value)

        IsError.isError(source)
            .assertValue(false)
    }
}