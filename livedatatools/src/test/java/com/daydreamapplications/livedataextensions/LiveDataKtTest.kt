package com.daydreamapplications.livedatatools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class LiveDataKtTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `emptyMutableLiveData - returns MutableLiveData - with no value`() {
        emptyMutableLiveData<Unit>()
            .assertNoValue()
    }

    @Test
    fun `mutableLiveDataOf - returns MutableLiveData - with given value`() {
        mutableLiveDataOf(Unit)
            .assertValue(Unit)
    }

    @Test
    fun `emptyLiveData - returns LiveData - with no value`() {
        emptyLiveData<Unit>()
            .assertNoValue()
    }

    @Test
    fun `mutableLiveDataOf - returns LiveData - with given value`() {
        mutableLiveDataOf(Unit)
            .assertValue(Unit)
    }

    @Test
    fun `flatMap - single source - has value from source`() {
        flatMap(liveDataOf(Unit))
            .assertValue(Unit)
    }

    @Test
    fun `flatMap - two source, first has value - has value from first source`() {
        flatMap(liveDataOf(Unit), emptyLiveData())
            .assertValue(Unit)
    }

    @Test
    fun `flatMap - two source, second has value - has value from second source`() {
        flatMap(emptyLiveData(), liveDataOf(Unit))
            .assertValue(Unit)
    }

    @Test
    fun `flatMap - two source, both have value - has values from both sources`() {
        flatMap(liveDataOf(Unit), liveDataOf(Unit))
            .assertValueHistory(Unit, Unit)
    }

    @Test
    fun `zipNonNull - single source has value - has no value`() {
        val mapper: (String, String) -> Unit? = mockk()
        val source1 = liveDataOf("1")
        val source2 = liveDataOf<String>(null)

        zipNonNull(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull - both sources have values - returns mapped value`() {
        val mapper: (String, String) -> Unit? = mockk()
        every { mapper("1", "2") } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")

        zipNonNull(source1, source2, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2")
            mapper("1", "2")
        }
    }
}