package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.Test

class ZipTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `zip(2 sources) - first source has value, second has no value - returns mapped value with null`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = emptyLiveData<String>()

        Zip.zip(source1, source2, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null)
            mapper("1", null)
        }
    }

    @Test
    fun `zip(2 sources) - first source has value, second has null - returns mapped value with null`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf<String>(null)

        Zip.zip(source1, source2, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null)
            mapper("1", null)
        }
    }

    @Test
    fun `zip(2 sources) - both sources have values - returns mapped value`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", "2") } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")

        Zip.zip(source1, source2, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2")
            mapper("1", "2")
        }
    }

    @Test
    fun `zip(3 sources) - single source has value - returns mapped value with null`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", null, null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = emptyLiveData<String>()
        val source3 = emptyLiveData<String>()

        Zip.zip(source1, source2, source3, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null, null)
            mapper("1", null, null)
            mapper("1", null, null)
        }
    }

    @Test
    fun `zip(3 sources) - two sources have values - returns mapped value with null`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", "2", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")
        val source3 = emptyLiveData<String>()

        Zip.zip(source1, source2, source3, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2", null)
            mapper("1", "2", null)
        }
    }

    @Test
    fun `zip(3 sources) - all three sources have values - returns mapped value`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", "2", "3") } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")
        val source3 = liveDataOf("3")

        Zip.zip(source1, source2, source3, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2", "3")
            mapper("1", "2", "3")
            mapper("1", "2", "3")
        }
    }
}