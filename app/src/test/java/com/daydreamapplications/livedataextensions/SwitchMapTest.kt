package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.livedata.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SwitchMapTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(SwitchMap)
    }

    @After
    fun tearDown() {
        unmockkObject(SwitchMap)
    }

    // Object functionality tests

    @Test
    fun `switchMap - source has no value - no value, switchFunction not invoked`() {
        val switchFunction: (Unit?) -> LiveData<String> = mockk()

        SwitchMap.switchMap(
            emptyLiveData(), switchFunction)
            .assertNoValue()
    }

    @Test
    fun `switchMap - source has value - returns switchFunction result`() {
        val switchFunction: (Unit?) -> LiveData<String> = mockk()
        val switchedLiveData =
            liveDataOf("mapped value")
        every { switchFunction(Unit) } returns switchedLiveData

        SwitchMap.switchMap(
            liveDataOf(Unit), switchFunction)
            .assertValue("mapped value")

        verifySequence {
            switchFunction(Unit)
        }
    }

    @Test
    fun `switchMapNonNull - source has no value - no value, switchFunction not invoked`() {
        val switchFunction: (Unit?) -> LiveData<String> = mockk()

        SwitchMap.switchMapNonNull(
            emptyLiveData(), switchFunction)
            .assertNoValue()
    }

    @Test
    fun `switchMapNonNull - source has non-null value - returns switchFunction result`() {
        val switchFunction: (Unit) -> LiveData<String> = mockk()
        val switchedLiveData =
            liveDataOf("mapped value")
        every { switchFunction(Unit) } returns switchedLiveData

        SwitchMap.switchMapNonNull(
            liveDataOf(Unit), switchFunction)
            .assertValue("mapped value")

        verifySequence {
            switchFunction(Unit)
        }
    }

    @Test
    fun `switchMapNonNull - source has null value - no value, switchFunction not invoked`() {
        val switchFunction: (Unit) -> LiveData<String> = mockk()

        SwitchMap.switchMapNonNull(
            liveDataOf<Unit>(
                null
            ), switchFunction)
            .assertNoValue()
    }

    // Extension Function Tests

    @Test
    fun `switchMap extension function - invokes SwitchMap switchMap`() {
        val switchFunction: (Unit?) -> LiveData<String> = mockk()
        val source =
            liveDataOf(Unit)

        every { SwitchMap.switchMap(source, switchFunction) } returns mockk()

        source.switchMap(switchFunction)

        verifySequence {
            SwitchMap.switchMap(source, switchFunction)
        }
    }

    @Test
    fun `switchMapNonNull extension function - invokes SwitchMap switchMapNonNull`() {
        val switchFunction: (Unit?) -> LiveData<String> = mockk()
        val source =
            liveDataOf(Unit)

        every { SwitchMap.switchMapNonNull(source, switchFunction) } returns mockk()

        source.switchMapNonNull(switchFunction)

        verifySequence {
            SwitchMap.switchMapNonNull(source, switchFunction)
        }
    }
}