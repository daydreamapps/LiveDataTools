package com.daydreamapplications.livedataextensions.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedataextensions.assertNoValue
import com.daydreamapplications.livedataextensions.test
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TakeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Take)
    }

    @After
    fun tearDown() {
        unmockkObject(Take)
    }

    // Object functionality tests

    @Test
    fun `take - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()

        Take.take(source, 1).assertNoValue()
    }

    @Test
    fun `take - source emits values, count 0 - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.take(source, 0).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `take - source emits values, count 1 - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.take(source, 1).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertValueHistory(null)
    }

    @Test
    fun `take - source emits values, count 2 - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.take(source, 2).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(null)

        observer.assertValueHistory(null, null)
    }

    @Test
    fun `takeNonNull - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()

        Take.takeNonNull(source, 1).assertNoValue()
    }

    @Test
    fun `takeNonNull - source emits null values, count 0 - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeNonNull(source, 0).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeNonNull - source emits non-null values, count 0 - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeNonNull(source, 0).test()

        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertNoValue()
    }

    @Test
    fun `takeNonNull - source emits null values, count 1 - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeNonNull(source, 1).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeNonNull - source emits non-null values, count 1 - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeNonNull(source, 1).test()

        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    @Test
    fun `takeNonNull - source emits null values then non-null values, count 1 - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeNonNull(source, 1).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    @Test
    fun `takeFirst - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()

        Take.takeFirst(source).assertNoValue()
    }

    @Test
    fun `takeFirst - source emits null values - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirst(source).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertValueHistory(null)
    }

    @Test
    fun `takeFirst - source emits non-null values - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirst(source).test()

        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    @Test
    fun `takeFirst - source emits null values then non-null values - returns LiveData with one null value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirst(source).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(null)
    }

    @Test
    fun `takeFirstNonNull - source has no value - returns LiveData with no value`() {
        val source = emptyLiveData<Unit>()

        Take.takeFirstNonNull(source).assertNoValue()
    }

    @Test
    fun `takeFirstNonNull - source emits null values - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirstNonNull(source).test()

        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeFirstNonNull - source emits non-null values - returns LiveData with one value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirstNonNull(source).test()

        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    @Test
    fun `takeFirstNonNull - source emits null values then non-null values - returns LiveData with one non-null value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeFirstNonNull(source).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    @Test
    fun `takeWhile - source emits no value - returns LiveData no value`() {
        val source = emptyMutableLiveData<Unit>()

        Take.takeWhile(source, mockk()).assertNoValue()
    }

    @Test
    fun `takeWhile - source emits null values, predicate return true - returns LiveData with null values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = true)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(null)
        source.postValue(null)

        observer.assertValueHistory(null, null, null, null)
    }

    @Test
    fun `takeWhile - source emits non-null values, predicate return true - returns LiveData with non-null values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = true)).test()

        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit, Unit, Unit, Unit)
    }

    @Test
    fun `takeWhile - source emits null values, then non-null values, predicate return true - returns LiveData with null and non-null values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = true)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(null, null, Unit, Unit)
    }

    @Test
    fun `takeWhile - source emits null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = false)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhile - source emits non-null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = false)).test()

        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhile - source emits null values, then non-null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhile(source, TestPredicate(returnValue = false)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhile - source emits null values, predicate return true then false - returns LiveData with values while true`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhile(source, testPredicate).test()

        source.postValue(null)
        source.postValue(null)
        testPredicate.returnValue = false
        source.postValue(null)
        source.postValue(null)

        observer.assertValueHistory(null, null)
    }

    @Test
    fun `takeWhile - source emits non-null values, predicate return true then false - returns LiveData with values while true`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhile(source, testPredicate).test()

        source.postValue(Unit)
        source.postValue(Unit)
        testPredicate.returnValue = false
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit, Unit)
    }

    @Test
    fun `takeWhile - source emits null values, then non-null values, predicate return true then false - returns LiveData with values while true`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhile(source, testPredicate).test()

        source.postValue(null)
        source.postValue(null)
        testPredicate.returnValue = false
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(null, null)
    }

    @Test
    fun `takeWhileNonNull - source emits no value - returns LiveData no value`() {
        val source = emptyMutableLiveData<Unit>()

        Take.takeWhileNonNull(source, mockk()).assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits null values, predicate return true - returns LiveData with no values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = true)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits non-null values, predicate return true - returns LiveData with non-null values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = true)).test()

        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit, Unit, Unit, Unit)
    }

    @Test
    fun `takeWhileNonNull - source emits null values, then non-null values, predicate return true - returns LiveData with non-null values`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = true)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit, Unit)
    }

    @Test
    fun `takeWhileNonNull - source emits null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = false)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits non-null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = false)).test()

        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits null values, then non-null values, predicate return false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val observer = Take.takeWhileNonNull(source, TestPredicate(returnValue = false)).test()

        source.postValue(null)
        source.postValue(null)
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits null values, predicate return true then false - returns LiveData with no value`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhileNonNull(source, testPredicate).test()

        source.postValue(null)
        source.postValue(null)
        testPredicate.returnValue = false
        source.postValue(null)
        source.postValue(null)

        observer.assertNoValue()
    }

    @Test
    fun `takeWhileNonNull - source emits non-null values, predicate return true then false - returns LiveData with values while true`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhileNonNull(source, testPredicate).test()

        source.postValue(Unit)
        source.postValue(Unit)
        testPredicate.returnValue = false
        source.postValue(Unit)
        source.postValue(Unit)

        observer.assertValueHistory(Unit, Unit)
    }

    @Test
    fun `takeWhileNonNull - source emits null values, then non-null values, predicate return true then false - returns LiveData with non-null values while true`() {
        val source = emptyMutableLiveData<Unit>()

        val testPredicate = TestPredicate(returnValue = true)
        val observer = Take.takeWhileNonNull(source, testPredicate).test()

        source.postValue(null)
        source.postValue(Unit)
        testPredicate.returnValue = false
        source.postValue(null)
        source.postValue(Unit)

        observer.assertValueHistory(Unit)
    }

    // Extension Function Tests

    @Test
    fun `take - delegates to Take`() {
        val source = liveDataOf(Unit)

        val returnLiveData = emptyLiveData<Unit>()
        every { Take.take(source, 3) } returns returnLiveData

        assertThat(source.take(3)).isEqualTo(returnLiveData)

        verify { Take.take(source, 3) }
    }

    @Test
    fun `takeNonNull - delegates to Take`() {
        val source = liveDataOf(Unit)

        val returnLiveData = emptyLiveData<Unit>()
        every { Take.takeNonNull(source, 3) } returns returnLiveData

        assertThat(source.takeNonNull(3)).isEqualTo(returnLiveData)

        verify { Take.takeNonNull(source, 3) }
    }

    @Test
    fun `takeFirst - delegates to Take`() {
        val source = liveDataOf(Unit)
        val returnLiveData = emptyLiveData<Unit>()
        every { Take.takeFirst(source) } returns returnLiveData

        assertThat(source.takeFirst()).isEqualTo(returnLiveData)

        verify { Take.takeFirst(source) }
    }

    @Test
    fun `takeFirstNonNull - delegates to Take`() {
        val source = liveDataOf(Unit)
        val returnLiveData = emptyLiveData<Unit>()
        every { Take.takeFirstNonNull(source) } returns returnLiveData

        assertThat(source.takeFirstNonNull()).isEqualTo(returnLiveData)

        verify { Take.takeFirstNonNull(source) }
    }

    @Test
    fun `takeWhile - delegates to Take`() {
        val source = liveDataOf(Unit)
        val predicate: (Unit?) -> Boolean = mockk()

        val returnLiveData = emptyLiveData<Unit>()
        every { Take.takeWhile(source, predicate) } returns returnLiveData

        assertThat(source.takeWhile(predicate)).isEqualTo(returnLiveData)

        verify { Take.takeWhile(source, predicate) }
    }

    @Test
    fun `takeWhileNonNull - delegates to Take`() {
        val source = liveDataOf(Unit)
        val predicate: (Unit) -> Boolean = mockk()

        val returnLiveData = emptyLiveData<Unit>()
        every { Take.takeWhileNonNull(source, predicate) } returns returnLiveData

        assertThat(source.takeWhileNonNull(predicate)).isEqualTo(returnLiveData)

        verify { Take.takeWhileNonNull(source, predicate) }
    }


    // Helper class

    private class TestPredicate(var returnValue: Boolean) : ((Unit?) -> Boolean) {

        override fun invoke(p1: Unit?): Boolean = returnValue
    }
}