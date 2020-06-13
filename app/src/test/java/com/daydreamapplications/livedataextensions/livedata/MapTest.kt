package com.daydreamapplications.livedataextensions.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.assertValue
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Map)
    }

    @After
    fun tearDown() {
        unmockkObject(Map)
    }

    // Object functionality tests

    @Test
    fun `map - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()
        val transform: (Unit?) -> String? = mockk()

        Map.map(source, transform).assertNoValue()
    }

    @Test
    fun `map - source value is null - returns LiveData with transformed value`() {
        val source = liveDataOf<Unit>(null)
        val transform: (Unit?) -> String? = mockk()

        every { transform(null) } returns "transformed"

        Map.map(source, transform).assertValue("transformed")

        verify { transform(null) }
    }

    @Test
    fun `map - source value is non-null - returns LiveData with transformed value`() {
        val source = liveDataOf(Unit)
        val transform: (Unit?) -> String? = mockk()

        every { transform(Unit) } returns "transformed"

        Map.map(source, transform).assertValue("transformed")

        verify { transform(Unit) }
    }

    @Test
    fun `mapNonNull - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()
        val transform: (Unit) -> String = mockk()

        Map.mapNonNull(source, transform).assertNoValue()
    }

    @Test
    fun `mapNonNull - source value is null - returns LiveData with no value`() {
        val source = liveDataOf<Unit>(null)
        val transform: (Unit) -> String = mockk()

        Map.mapNonNull(source, transform).assertNoValue()
    }

    @Test
    fun `mapNonNull - source value is non-null - returns LiveData with transformed value`() {
        val source = liveDataOf(Unit)
        val transform: (Unit) -> String = mockk()

        every { transform(Unit) } returns "transformed"

        Map.mapNonNull(source, transform).assertValue("transformed")

        verify { transform(Unit) }
    }

    // Extension Function Tests

    @Test
    fun `map - delegates to Map - returns LiveData`() {
        val source = emptyLiveData<Unit>()
        val transform: (Unit?) -> String? = mockk()

        every { Map.map(source, transform) } returns liveDataOf("transform")

        source.map(transform).assertValue("transform")

        verify { Map.map(source, transform) }
    }

    @Test
    fun `mapNonNull - delegates to Map - returns LiveData`() {
        val source = emptyLiveData<Unit>()
        val transform: (Unit) -> String = mockk()

        every { Map.mapNonNull(source, transform) } returns liveDataOf("transform")

        source.mapNonNull(transform).assertValue("transform")

        verify { Map.mapNonNull(source, transform) }
    }
}