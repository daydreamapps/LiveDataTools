package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.livedata.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DoOnNextKtTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(DoOnNext)
    }

    @After
    fun tearDown() {
        unmockkObject(DoOnNext)
    }

    // Object functionality tests

    @Test
    fun `doOnNext - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit?) -> Unit = mockk()

        DoOnNext.doOnNext(source, action).assertNoValue()
    }

    @Test
    fun `doOnNext - source value is null - action invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit?) -> Unit = mockk()
        every { action(null) } just Runs

        DoOnNext.doOnNext(source, action).assertValueIsNull()

        verify { action(null) }
    }

    @Test
    fun `doOnNext - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOnNext.doOnNext(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    @Test
    fun `doOnNextNonNull - source has no value - action is not invoked`() {
        val source = emptyLiveData<Unit>()
        val action: (Unit) -> Unit = mockk()

        DoOnNext.doOnNextNonNull(source, action).assertNoValue()
    }

    @Test
    fun `doOnNextNonNull - source value is null - action is not invoked`() {
        val source = liveDataOf<Unit>(null)
        val action: (Unit) -> Unit = mockk()

        DoOnNext.doOnNextNonNull(source, action).assertValueIsNull()
    }

    @Test
    fun `doOnNextNonNull - source value is non-null - action invoked`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()
        every { action(Unit) } just Runs

        DoOnNext.doOnNextNonNull(source, action).assertValue(Unit)

        verify { action(Unit) }
    }

    // Extension Function Tests

    @Test
    fun `doOnNext extension function - delegates to DoOnNext doOnNext`() {
        val source = liveDataOf(Unit)
        val action: (Unit?) -> Unit = mockk()

        every { DoOnNext.doOnNext(source, action) } returns source

        source.doOnNext(action).assertValue(Unit)

        verify { DoOnNext.doOnNext(source, action) }
    }

    @Test
    fun `doOnNextNonNull extension function - delegates to DoOnNext doOnNextNonNull`() {
        val source = liveDataOf(Unit)
        val action: (Unit) -> Unit = mockk()

        every { DoOnNext.doOnNextNonNull(source, action) } returns source

        source.doOnNextNonNull(action).assertValue(Unit)

        verify { DoOnNext.doOnNextNonNull(source, action) }
    }
}