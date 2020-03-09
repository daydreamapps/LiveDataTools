package com.daydreamapplications.livedataextensions.result.state

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import com.daydreamapplications.livedataextensions.result.Result
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class IsLoadingTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `isLoading - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        IsLoading.isLoading(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(false)
    }

    @Test
    fun `isLoading - value is Result Loading - value is true`() {
        val value: Result.Loading = mockk()

        IsLoading.isLoading(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(true)
    }

    @Test
    fun `isLoading - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        IsLoading.isLoading(
            liveDataOf<Result<Unit>>(
                value
            )
        )
            .assertValue(false)
    }
}