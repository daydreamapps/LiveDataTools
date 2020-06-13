package com.daydreamapplications.livedataextensions.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.assertFalse
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.assertTrue
import com.daydreamapplications.livedataextensions.livedata.emptyLiveData
import com.daydreamapplications.livedataextensions.livedata.liveDataOf
import com.daydreamapplications.livedataextensions.util.eraseType
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class SuccessTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Success)
    }

    @After
    fun tearDown() {
        unmockkObject(Success)
    }

    // Object functionality tests

    @Test
    fun `isSuccess - source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>()

        Success.isSuccess(source).assertNoValue()
    }

    @Test
    fun `isSuccess - source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null)

        Success.isSuccess(source).assertFalse()
    }

    @Test
    fun `isSuccess - source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading())

        Success.isSuccess(source).assertFalse()
    }

    @Test
    fun `isSuccess - source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk()))

        Success.isSuccess(source).assertFalse()
    }

    @Test
    fun `isSuccess - source LiveData has value Success - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit))

        Success.isSuccess(source).assertTrue()
    }

    @Test
    fun `anySuccess - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Success.anySuccess(source).assertNoValue()
    }

    @Test
    fun `anySuccess - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Success.anySuccess(source).assertFalse()
    }

    @Test
    fun `anySuccess - single source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Success.anySuccess(source).assertFalse()
    }

    @Test
    fun `anySuccess - single source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Success.anySuccess(source).assertFalse()
    }

    @Test
    fun `anySuccess - single source LiveData has value Success - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Success.anySuccess(source).assertTrue()
    }

    @Test
    fun `allSuccess - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Success.allSuccess(source).assertNoValue()
    }

    @Test
    fun `allSuccess - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Success.allSuccess(source).assertFalse()
    }

    @Test
    fun `allSuccess - single source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Success.allSuccess(source).assertFalse()
    }

    @Test
    fun `allSuccess - single source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Success.allSuccess(source).assertFalse()
    }

    @Test
    fun `allSuccess - single source LiveData has value Success - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Success.allSuccess(source).assertTrue()
    }

    // Extension Function Tests

    @Test
    fun `isSuccess extension function - delegates to Success isSuccess`() {
        val source = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.isSuccess(source) } returns returnedLiveData

        assertThat(source.isSuccess()).isEqualTo(returnedLiveData)

        verify { Success.isSuccess(source) }
    }

    @Test
    fun `anySuccess(2) extension function - delegates to Success anySuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.anySuccess(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(anySuccess(source1, source2)).isEqualTo(returnedLiveData)

        verify { Success.anySuccess(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `anySuccess(3) extension function - delegates to Success anySuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(anySuccess(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `anySuccess(4) extension function - delegates to Success anySuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(anySuccess(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }

    @Test
    fun `allSuccess(2) extension function - delegates to Success allSuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.allSuccess(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(allSuccess(source1, source2)).isEqualTo(returnedLiveData)

        verify { Success.allSuccess(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `allSuccess(3) extension function - delegates to Success allSuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(allSuccess(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `allSuccess(4) extension function - delegates to Success allSuccess`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(allSuccess(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }
}