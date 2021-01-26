package com.daydreamapplications.livedatatools.livedata.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.assertFalse
import com.daydreamapplications.livedatatools.assertNoValue
import com.daydreamapplications.livedatatools.assertTrue
import com.daydreamapplications.livedatatools.livedata.emptyLiveData
import com.daydreamapplications.livedatatools.livedata.liveDataOf
import com.daydreamapplications.livedatatools.util.eraseType
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class LoadingTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Loading)
    }

    @After
    fun tearDown() {
        unmockkObject(Loading)
    }

    // Object functionality tests

    @Test
    fun `isLoading - source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>()

        Loading.isLoading(source).assertNoValue()
    }

    @Test
    fun `isLoading - source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null)

        Loading.isLoading(source).assertFalse()
    }

    @Test
    fun `isLoading - source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit))

        Loading.isLoading(source).assertFalse()
    }

    @Test
    fun `isLoading - source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk()))

        Loading.isLoading(source).assertFalse()
    }

    @Test
    fun `isLoading - source LiveData has value Loading - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.loading())

        Loading.isLoading(source).assertTrue()
    }

    @Test
    fun `anyLoading - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Loading.anyLoading(source).assertNoValue()
    }

    @Test
    fun `anyLoading - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Loading.anyLoading(source).assertFalse()
    }

    @Test
    fun `anyLoading - single source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Loading.anyLoading(source).assertFalse()
    }

    @Test
    fun `anyLoading - single source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Loading.anyLoading(source).assertFalse()
    }

    @Test
    fun `anyLoading - single source LiveData has value Loading - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Loading.anyLoading(source).assertTrue()
    }

    @Test
    fun `allLoading - single source LiveData has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Result<Unit>>() as LiveData<Result<*>>

        Loading.allLoading(source).assertNoValue()
    }

    @Test
    fun `allLoading - single source LiveData has null value - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(null) as LiveData<Result<*>>

        Loading.allLoading(source).assertFalse()
    }

    @Test
    fun `allLoading - single source LiveData has value Success - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.success(Unit)) as LiveData<Result<*>>

        Loading.allLoading(source).assertFalse()
    }

    @Test
    fun `allLoading - single source LiveData has value Error - returns LiveData with value false`() {
        val source = liveDataOf<Result<Unit>>(Result.error(mockk())) as LiveData<Result<*>>

        Loading.allLoading(source).assertFalse()
    }

    @Test
    fun `allLoading - single source LiveData has value Loading - returns LiveData with value true`() {
        val source = liveDataOf<Result<Unit>>(Result.loading()) as LiveData<Result<*>>

        Loading.allLoading(source).assertTrue()
    }

    // Extension Function Tests

    @Test
    fun `isLoading extension function - delegates to Loading isLoading`() {
        val source = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.isLoading(source) } returns returnedLiveData

        assertThat(source.isLoading()).isEqualTo(returnedLiveData)

        verify { Loading.isLoading(source) }
    }

    @Test
    fun `anyLoading(2) extension function - delegates to Loading anyLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.anyLoading(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(anyLoading(source1, source2)).isEqualTo(returnedLiveData)

        verify { Loading.anyLoading(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `anyLoading(3) extension function - delegates to Loading anyLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(anyLoading(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `anyLoading(4) extension function - delegates to Loading anyLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(anyLoading(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }

    @Test
    fun `allLoading(2) extension function - delegates to Loading allLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.allLoading(source1.eraseType(), source2.eraseType()) } returns returnedLiveData

        assertThat(allLoading(source1, source2)).isEqualTo(returnedLiveData)

        verify { Loading.allLoading(source1.eraseType(), source2.eraseType()) }
    }

    @Test
    fun `allLoading(3) extension function - delegates to Loading allLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType()) } returns returnedLiveData

        assertThat(allLoading(source1, source2, source3)).isEqualTo(returnedLiveData)

        verify { Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType()) }
    }

    @Test
    fun `allLoading(4) extension function - delegates to Loading allLoading`() {
        val source1 = liveDataOf(Result.loading<Unit>())
        val source2 = liveDataOf(Result.loading<Unit>())
        val source3 = liveDataOf(Result.loading<Unit>())
        val source4 = liveDataOf(Result.loading<Unit>())
        val returnedLiveData = liveDataOf(true)

        every { Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) } returns returnedLiveData

        assertThat(allLoading(source1, source2, source3, source4)).isEqualTo(returnedLiveData)

        verify { Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType()) }
    }
}