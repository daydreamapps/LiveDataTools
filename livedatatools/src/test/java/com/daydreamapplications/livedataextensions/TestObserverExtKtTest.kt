package com.daydreamapplications.livedatatools

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestObserverExtKtTest {

    private val observer: TestObserver<Unit> = mockk()
    private val liveData: LiveData<Unit> = mockk()

    @Before
    fun setUp() {
        mockkObject(TestObserver.Companion)
        every { TestObserver.create<Unit>() } returns observer
        every { liveData.observeForever(observer) } just Runs
    }

    @After
    fun tearDown() {
        unmockkObject(TestObserver.Companion)
        verify {
            TestObserver.create<Unit>()
            liveData.observeForever(observer)
        }
    }

    @Test
    fun `test - observes forever with observer - returns observer`() {
        liveData.test() // sanity check for teardown
    }

    @Test
    fun `value - calls observer value`() {
        every { observer.value() } returns Unit

        liveData.value()

        verify {
            observer.value()
        }
    }

    @Test
    fun `valueHistory - calls observer valueHistory`() {
        every { observer.valueHistory() } returns listOf(Unit, Unit)

        assertThat(liveData.valueHistory()).isEqualTo(listOf(Unit, Unit))

        verify {
            observer.valueHistory()
        }
    }

    @Test
    fun `assertHasValue - calls observer assertHasValue`() {
        every { observer.assertHasValue() } returns observer

        liveData.assertHasValue()

        verify {
            observer.assertHasValue()
        }
    }

    @Test
    fun `assertValue - calls observer assertValue`() {
        every { observer.assertValue(Unit) } returns observer

        liveData.assertValue(Unit)

        verify {
            observer.assertValue(Unit)
        }
    }

    @Test
    fun `assertValue predicate - calls observer assertValue`() {
        val predicate: Function<Unit?, Boolean?> = mockk()
        every { observer.assertValue(predicate) } returns observer

        liveData.assertValue(predicate)

        verify {
            observer.assertValue(predicate)
        }
    }

    @Test
    fun `assertValueIsNull - calls observer assertValue`() {
        every { observer.assertValue(null) } returns observer

        liveData.assertValueIsNull()

        verify {
            observer.assertValue(null)
        }
    }

    @Test
    fun `assertValueHistory - calls observer assertValueHistory`() {
        every { observer.assertValueHistory(Unit, Unit) } returns observer

        liveData.assertValueHistory(Unit, Unit)

        verify {
            observer.assertValueHistory(Unit, Unit)
        }
    }

    @Test
    fun `assertNoValue - calls observer assertNoValue`() {
        every { observer.assertNoValue() } returns observer

        liveData.assertNoValue()

        verify {
            observer.assertNoValue()
        }
    }

    @Test
    fun `assertNever - calls observer assertNever`() {
        val predicate: Function<Unit, Boolean> = mockk()
        every { observer.assertNever(predicate) } returns observer

        liveData.assertNever(predicate)

        verify {
            observer.assertNever(predicate)
        }
    }
}