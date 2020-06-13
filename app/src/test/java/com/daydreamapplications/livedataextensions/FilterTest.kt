package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.livedata.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FilterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Filter)
    }

    @After
    fun tearDown() {
        unmockkObject(Filter)
    }

    // Object functionality tests

    @Test
    fun `filter - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        val source =
            liveDataOf(Unit)
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

        val source =
            liveDataOf(Unit)
        Filter.filter(source, predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filterNonNull - value is null - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()

        Filter.filterNonNull(
            liveDataOf<Unit>(
                null
            ), predicate)
            .assertNoValue()

        verify {
            predicate wasNot Called
        }
    }

    @Test
    fun `filterNonNull - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        val source =
            liveDataOf(Unit)
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

        val source =
            liveDataOf(Unit)
        Filter.filterNonNull(source, predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }

    // Extension Function Tests

    @Test
    fun `filter extension function - calls Filter filter`() {
        val source =
            emptyLiveData<Unit>()
        val predicate: (Unit?) -> Boolean = mockk()

        every { Filter.filter(source, predicate) } returns mockk()

        source.filter(predicate)

        verify {
            Filter.filter(source, predicate)
        }
    }

    @Test
    fun `filterNonNull extension function - calls Filter filterNonNull`() {
        val source =
            emptyLiveData<Unit>()
        val predicate: (Unit) -> Boolean = mockk()

        every { Filter.filterNonNull(source, predicate) } returns mockk()

        source.filterNonNull(predicate)

        verify {
            Filter.filterNonNull(source, predicate)
        }
    }
}