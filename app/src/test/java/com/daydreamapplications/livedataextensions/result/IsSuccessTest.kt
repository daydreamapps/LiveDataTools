package com.daydreamapplications.livedataextensions.result

import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.liveDataOf
import io.mockk.mockk
import org.junit.Test

class IsSuccessTest {

    @Test
    fun `isSuccess - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        IsSuccess.isSuccess(liveDataOf<Result<Unit>>(value))
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        IsSuccess.isSuccess(liveDataOf<Result<Unit>>(value))
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        IsSuccess.isSuccess(liveDataOf<Result<Unit>>(value))
            .assertValue(true)
    }
}