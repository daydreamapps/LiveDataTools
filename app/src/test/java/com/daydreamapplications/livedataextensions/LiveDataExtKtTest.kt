package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class LiveDataExtKtTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `map - value is null - returns mapped value`() {
        val mapper: (Unit?) -> String? = mockk()
        every { mapper(null) } returns "mapped value"

        liveDataOf<Unit>(null)
            .map(mapper)
            .assertValue("mapped value")

        verifySequence {
            mapper(null)
        }
    }

    @Test
    fun `map - value is nonnull - returns mapped value`() {
        val mapper: (Unit?) -> String? = mockk()
        every { mapper(Unit) } returns "mapped value"

        liveDataOf(Unit)
            .map(mapper)
            .assertValue("mapped value")

        verifySequence {
            mapper(Unit)
        }
    }

    @Test
    fun `switchMap - source has value - returns LiveData observing mapper result`() {
        val mapper: (Unit?) -> LiveData<String> = mockk()
        val switchedLiveData = liveDataOf("mapped value")
        every { mapper(Unit) } returns switchedLiveData

        liveDataOf(Unit)
            .switchMap(mapper)
            .assertValue("mapped value")

        verifySequence {
            mapper(Unit)
        }
    }

    @Test
    fun `mapNonNull - value is null - returns null`() {
        val mapNonNull: (Unit) -> String? = mockk()

        liveDataOf<Unit>(null)
            .mapNonNull(mapNonNull)
            .assertNoValue()

        verifySequence {
            mapNonNull wasNot Called
        }
    }

    @Test
    fun `mapNonNull - value is nonnull - returns null`() {
        val mapNonNull: (Unit) -> String? = mockk()
        every { mapNonNull(Unit) } returns "mapped value"

        liveDataOf(Unit)
            .mapNonNull(mapNonNull)
            .assertValue("mapped value")

        verifySequence {
            mapNonNull(Unit)
        }
    }

    @Test
    fun `filter - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        liveDataOf(Unit)
            .filter(predicate)
            .assertNoValue()

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filter - predicate returns true for value - value is passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        liveDataOf(Unit)
            .filter(predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filterNonNull - value is null - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()

        liveDataOf<Unit>(null)
            .filterNonNull(predicate)
            .assertNoValue()

        verify {
            predicate wasNot Called
        }
    }

    @Test
    fun `filterNonNull - predicate returns false for value - value is not passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        liveDataOf(Unit)
            .filterNonNull(predicate)
            .assertNoValue()

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `filterNonNull - predicate returns true for value - value is passed`() {
        val predicate: (Unit?) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        liveDataOf(Unit)
            .filterNonNull(predicate)
            .assertValue(Unit)

        verifySequence {
            predicate(Unit)
        }
    }

    @Test
    fun `doOnNext - liveData has no value - onNext is not called`() {
        val onNext: (Unit?) -> Unit = mockk()

        liveDataOf(Unit)
            .doOnNext(onNext)

        verifySequence {
            onNext wasNot Called
        }
    }

    @Test
    fun `doOnNext - liveData has null value - onNext is invoked`() {
        val onNext: (Unit?) -> Unit = mockk()
        every { onNext(null) } just Runs

        liveDataOf<Unit>(null)
            .doOnNext(onNext)
            .test()

        verifySequence {
            onNext(null)
        }
    }

    @Test
    fun `doOnNext - liveData has nonull value - onNext is invoked`() {
        val onNext: (Unit?) -> Unit = mockk()
        every { onNext(Unit) } just Runs

        liveDataOf(Unit)
            .doOnNext(onNext)
            .test()

        verifySequence {
            onNext(Unit)
        }
    }

    @Test
    fun `doOnNextNonNull - liveData has no value - onNext is not called`() {
        val onNext: (Unit?) -> Unit = mockk()

        liveDataOf(Unit)
            .doOnNextNonNull(onNext)

        verifySequence {
            onNext wasNot Called
        }
    }

    @Test
    fun `doOnNextNonNull - liveData has null value - onNext is not invoked`() {
        val onNext: (Unit?) -> Unit = mockk()

        liveDataOf<Unit>(null)
            .doOnNextNonNull(onNext)
            .test()

        verifySequence {
            onNext wasNot Called
        }
    }

    @Test
    fun `doOnNextNonNull - liveData has nonull value - onNext is invoked`() {
        val onNext: (Unit?) -> Unit = mockk()
        every { onNext(Unit) } just Runs

        liveDataOf(Unit)
            .doOnNextNonNull(onNext)
            .test()

        verifySequence {
            onNext(Unit)
        }
    }
}