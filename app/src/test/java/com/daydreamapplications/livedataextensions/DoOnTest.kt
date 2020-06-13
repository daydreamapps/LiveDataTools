package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.livedata.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DoOnTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(DoOn)
    }

    @After
    fun tearDown() {
        unmockkObject(DoOn)
    }

    // Object functionality tests

    @Test
    fun `doOnNext - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit?) -> Unit = mockk()

        DoOn.doOnNext(source, action).assertNoValue()
    }

    @Test
    fun `doOnNext - source value is null - action invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit?) -> Unit = mockk()
        every { action(null) } just Runs

        DoOn.doOnNext(source, action).assertValueIsNull()

        verify { action(null) }
    }

    @Test
    fun `doOnNext - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOn.doOnNext(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    @Test
    fun `doOnNextNonNull - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit) -> Unit = mockk()

        DoOn.doOnNextNonNull(source, action).assertNoValue()
    }

    @Test
    fun `doOnNextNonNull - source value is null - action is not invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit) -> Unit = mockk()

        DoOn.doOnNextNonNull(source, action).assertValueIsNull()
    }

    @Test
    fun `doOnNextNonNull - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOn.doOnNextNonNull(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    @Test
    fun `doOnFirst - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit?) -> Unit = mockk()

        DoOn.doOnFirst(source, action).assertNoValue()
    }

    @Test
    fun `doOnFirst - source value is null - action invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit?) -> Unit = mockk()
        every { action(null) } just Runs

        DoOn.doOnFirst(source, action).assertValueIsNull()

        verify { action(null) }
    }

    @Test
    fun `doOnFirst - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOn.doOnFirst(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    @Test
    fun `doOnFirstNonNull - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit) -> Unit = mockk()

        DoOn.doOnFirstNonNull(source, action).assertNoValue()
    }

    @Test
    fun `doOnFirstNonNull - source value is null - action is not invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit) -> Unit = mockk()

        DoOn.doOnFirstNonNull(source, action).assertValueIsNull()
    }

    @Test
    fun `doOnFirstNonNull - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOn.doOnFirstNonNull(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    // Extension Function Tests

    @Test
    fun `doOnNext extension function - delegates to DoOnNext doOnNext`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()

        every { DoOn.doOnNext(source, action) } returns source

        source.doOnNext(action).assertValue(Unit)

        verify { DoOn.doOnNext(source, action) }
    }

    @Test
    fun `doOnNextNonNull extension function - delegates to DoOnNext doOnNextNonNull`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()

        every { DoOn.doOnNextNonNull(source, action) } returns source

        source.doOnNextNonNull(action).assertValue(Unit)

        verify { DoOn.doOnNextNonNull(source, action) }
    }

    @Test
    fun `doOnFirst extension function - delegates to DoOnFirst doOnFirst`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()

        every { DoOn.doOnFirst(source, action) } returns source

        source.doOnFirst(action).assertValue(Unit)

        verify { DoOn.doOnFirst(source, action) }
    }

    @Test
    fun `doOnFirstNonNull extension function - delegates to DoOnFirst doOnFirstNonNull`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()

        every { DoOn.doOnFirstNonNull(source, action) } returns source

        source.doOnFirstNonNull(action).assertValue(Unit)

        verify { DoOn.doOnFirstNonNull(source, action) }
    }
}