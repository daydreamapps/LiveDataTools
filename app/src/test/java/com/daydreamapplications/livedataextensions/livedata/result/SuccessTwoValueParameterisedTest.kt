package com.daydreamapplications.livedataextensions.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertValue
import com.daydreamapplications.livedataextensions.livedata.liveDataOf
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SuccessTwoValueParameterisedTest(
    private val first: Result<*>?,
    private val second: Result<*>?,
    private val expectedAnySuccessValue: Boolean,
    private val expectedAllSuccessValue: Boolean
) {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `anySuccess - LiveData with expected value`() {
        val source1 = liveDataOf(first)
        val source2 = liveDataOf(second)

        Success.anySuccess(source1, source2).assertValue(expectedAnySuccessValue)
    }

    @Test
    fun `allSuccess - LiveData with expected value`() {
        val source1 = liveDataOf(first)
        val source2 = liveDataOf(second)

        Success.allSuccess(source1, source2).assertValue(expectedAllSuccessValue)
    }

    companion object {

        private fun parameter(
            first: Result<Unit>?,
            second: Result<Unit>?,
            any: Boolean,
            all: Boolean
        ): Array<Any?> = arrayOf(first, second, any, all)

        @Parameterized.Parameters(name = "first source value: {0}, second source value: {1}")
        @JvmStatic
        fun parameters(): Iterable<Array<Any?>> {
            return listOf(
                parameter(null, null, any = false, all = false),
                parameter(null, Result.loading(), any = false, all = false),
                parameter(null, Result.error(mockk()), any = false, all = false),
                parameter(null, Result.success(Unit), any = true, all = false),

                parameter(Result.loading(), null, any = false, all = false),
                parameter(Result.loading(), Result.loading(), any = false, all = false),
                parameter(Result.loading(), Result.error(mockk()), any = false, all = false),
                parameter(Result.loading(), Result.success(Unit), any = true, all = false),

                parameter(Result.error(mockk()), null, any = false, all = false),
                parameter(Result.error(mockk()), Result.loading(), any = false, all = false),
                parameter(Result.error(mockk()), Result.error(mockk()), any = false, all = false),
                parameter(Result.error(mockk()), Result.success(Unit), any = true, all = false),

                parameter(Result.success(Unit), null, any = true, all = false),
                parameter(Result.success(Unit), Result.loading(), any = true, all = false),
                parameter(Result.success(Unit), Result.error(mockk()), any = true, all = false),
                parameter(Result.success(Unit), Result.success(Unit), any = true, all = true)
            )
        }
    }
}