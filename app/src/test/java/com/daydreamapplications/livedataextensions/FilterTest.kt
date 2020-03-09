package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class FilterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `filter - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        val source = liveDataOf(Unit)
        Filter.filter(source, predicate)
            .assertNoValue()

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filter - predicate returns true for value - value is passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        val source = liveDataOf(Unit)
        Filter.filter(source, predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filterNonNull - value is null - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()

        Filter.filterNonNull(liveDataOf<Unit>(null), predicate)
            .assertNoValue()

        verify {
            predicate wasNot Called
        }
    }

    @Test
    fun `filterNonNull - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        val source = liveDataOf(Unit)
        Filter.filterNonNull(source, predicate)
            .assertNoValue()

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filterNonNull - predicate returns true for value - value is passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        val source = liveDataOf(Unit)
        Filter.filterNonNull(source, predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }
}