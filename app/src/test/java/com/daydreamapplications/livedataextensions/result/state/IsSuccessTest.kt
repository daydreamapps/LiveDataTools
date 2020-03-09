package com.daydreamapplications.livedataextensions.result.state

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import com.daydreamapplications.livedataextensions.result.Result
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class IsSuccessTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `isSuccess - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        IsSuccess.isSuccess(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        IsSuccess.isSuccess(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        IsSuccess.isSuccess(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(true)
    }
}