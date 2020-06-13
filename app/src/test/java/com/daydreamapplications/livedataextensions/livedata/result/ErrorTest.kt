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
class ErrorTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Error)
    }

    @After
    fun tearDown() {
        unmockkObject(Error)
    }

    // Object functionality tests

    @Test
    fun `isError - source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>()

        Error.isError(source).assertNoValue()
    }

    @Test
    fun `isError - source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null)

        Error.isError(source).assertFalse()
    }

    @Test
    fun `isError - source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading())

        Error.isError(source).assertFalse()
    }

    @Test
    fun `isError - source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit))

        Error.isError(source).assertFalse()
    }

    @Test
    fun `isError - source LiveData has value Error - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk()))

        Error.isError(source).assertTrue()
    }

    @Test
    fun `anyError - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Error.anyError(source).assertNoValue()
    }

    @Test
    fun `anyError - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Error.anyError(source).assertFalse()
    }

    @Test
    fun `anyError - single source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Error.anyError(source).assertFalse()
    }

    @Test
    fun `anyError - single source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Error.anyError(source).assertFalse()
    }

    @Test
    fun `anyError - single source LiveData has value Error - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Error.anyError(source).assertTrue()
    }

    @Test
    fun `allError - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Error.allError(source).assertNoValue()
    }

    @Test
    fun `allError - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Error.allError(source).assertFalse()
    }

    @Test
    fun `allError - single source LiveData has value Loading - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Error.allError(source).assertFalse()
    }

    @Test
    fun `allError - single source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Error.allError(source).assertFalse()
    }

    @Test
    fun `allError - single source LiveData has value Error - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Error.allError(source).assertTrue()
    }

    // Extension Function Tests

    @Test
    fun `isError extension function - delegates to Error isError`() {
        val source = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.isError(source) } returns returnedLiveData

        assertThat(source.isError()).isEqualTo(returnedLiveData)

        verify { Error.isError(source) }
    }

    @Test
    fun `anyError(2) extension function - delegates to Error anyError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.anyError(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(anyError(source1, source2)).isEqualTo(returnedLiveData)

        verify { Error.anyError(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `anyError(3) extension function - delegates to Error anyError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(anyError(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `anyError(4) extension function - delegates to Error anyError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(anyError(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }

    @Test
    fun `allError(2) extension function - delegates to Error allError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.allError(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(allError(source1, source2)).isEqualTo(returnedLiveData)

        verify { Error.allError(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `allError(3) extension function - delegates to Error allError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(allError(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `allError(4) extension function - delegates to Error allError`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(allError(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }
}