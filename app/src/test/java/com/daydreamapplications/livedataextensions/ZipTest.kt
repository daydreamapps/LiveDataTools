package com.daydreamapplications.livedataextensions

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Test

class ZipTest {

    @Test
    fun `zip - single source has value - returns mapped value`() {
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
    fun `zip - both sources have values - returns mapped value`() {
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
}