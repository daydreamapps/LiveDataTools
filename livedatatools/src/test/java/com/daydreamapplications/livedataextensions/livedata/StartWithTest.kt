package com.daydreamapplications.livedatatools.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedatatools.assertValueHistory
import com.daydreamapplications.livedatatools.test
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartWithTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(StartWith)
    }

    @After
    fun tearDown() {
        unmockkObject(StartWith)
    }

    // Object functionality tests

    @Test
    fun `startWith Items - source has no value - return LiveData that emits start item only`() {
        val source = emptyLiveData<Int>()

        StartWith.startWith(source, 1).assertValueHistory(1)
    }

    @Test
    fun `startWith Items - source has value - return LiveData that emits start item before source values`() {
        val source = emptyMutableLiveData<Int>()

        val observer = StartWith.startWith(source, 1).test()

        source.postValue(2)
        source.postValue(3)

        observer.assertValueHistory(1, 2, 3)
    }

    // Extension Function Tests

    @Test
    fun `startWith extension function - delegates StartWith startWith`() {
        val source = emptyLiveData<Int>()

        val returnLiveData = emptyLiveData<Int>()
        every { StartWith.startWith(source, 1) } returns returnLiveData

        assertThat(source.startWith(1)).isEqualTo(returnLiveData)

        verify { StartWith.startWith(source, 1) }
    }
}