package com.daydreamapplications.livedatatools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ZipTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(Zip)
    }

    @After
    fun tearDown() {
        unmockkObject(Zip)
    }

    // Object functionality tests

    @Test
    fun `zip2 - first source has value, second has no value - returns mapped value with null`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = emptyLiveData<String>()

        Zip.zip2(source1, source2, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null)
            mapper("1", null)
        }
    }

    @Test
    fun `zip2 - first source has value, second has null - returns mapped value with null`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf<String>(null)

        Zip.zip2(source1, source2, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null)
            mapper("1", null)
        }
    }

    @Test
    fun `zip2 - both sources have values - returns mapped value`() {
        val mapper: (String?, String?) -> Unit? = mockk()
        every { mapper("1", "2") } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")

        Zip.zip2(source1, source2, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2")
            mapper("1", "2")
        }
    }

    @Test
    fun `zip3 - single source has value - returns mapped value with null`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", null, null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = emptyLiveData<String>()
        val source3 = emptyLiveData<String>()

        Zip.zip3(source1, source2, source3, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", null, null)
            mapper("1", null, null)
            mapper("1", null, null)
        }
    }

    @Test
    fun `zip3 - two sources have values - returns mapped value with null`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", "2", null) } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")
        val source3 = emptyLiveData<String>()

        Zip.zip3(source1, source2, source3, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2", null)
            mapper("1", "2", null)
        }
    }

    @Test
    fun `zip3 - all three sources have values - returns mapped value`() {
        val mapper: (String?, String?, String?) -> Unit? = mockk()
        every { mapper("1", "2", "3") } returns Unit
        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")
        val source3 = liveDataOf("3")

        Zip.zip3(source1, source2, source3, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2", "3")
            mapper("1", "2", "3")
            mapper("1", "2", "3")
        }
    }

    @Test
    fun `zipNonNull2 - first source has no value, second has no value - has no value`() {
        val mapper: (String, String) -> Unit = mockk()

        val source1 = emptyLiveData<String>()
        val source2 = emptyLiveData<String>()

        Zip.zipNonNull2(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull2 - first source has no value, second has null - has no value`() {
        val mapper: (String, String) -> Unit = mockk()

        val source1 = emptyLiveData<String>()
        val source2 = liveDataOf<String>(null)

        Zip.zipNonNull2(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull2 - first source has null, second has null - has no value`() {
        val mapper: (String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>(null)
        val source2 = liveDataOf<String>(null)

        Zip.zipNonNull2(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull2 - first source has value, second has no value - has no value`() {
        val mapper: (String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = emptyLiveData<String>()

        Zip.zipNonNull2(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull2 - first source has value, second has null - has no value`() {
        val mapper: (String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = liveDataOf<String>(null)

        Zip.zipNonNull2(source1, source2, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull2 - both sources have value - returned mapped value`() {
        val mapper: (String, String) -> Unit = mockk()
        every { mapper("1", "2") } returns Unit

        val source1 = liveDataOf("1")
        val source2 = liveDataOf("2")

        Zip.zipNonNull2(source1, source2, mapper)
            .assertValue(Unit)

        verify {
            mapper("1", "2")
        }
    }

    @Test
    fun `zipNonNull3 - all sources have no value - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = emptyLiveData<String>()
        val source2 = emptyLiveData<String>()
        val source3 = emptyLiveData<String>()

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - one source has null, others have no value - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>(null)
        val source2 = emptyLiveData<String>()
        val source3 = emptyLiveData<String>()

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - two source2 have null, other has no value - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>(null)
        val source2 = liveDataOf<String>(null)
        val source3 = emptyLiveData<String>()

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - one source has value, others have no value - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = emptyLiveData<String>()
        val source3 = emptyLiveData<String>()

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - two source2 have value, other has no value - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = liveDataOf<String>("2")
        val source3 = emptyLiveData<String>()

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - one source has value, others has null - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = liveDataOf<String>(null)
        val source3 = liveDataOf<String>(null)

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - two source2 have value, other has  - has no value`() {
        val mapper: (String, String, String) -> Unit = mockk()

        val source1 = liveDataOf<String>("1")
        val source2 = liveDataOf<String>("2")
        val source3 = liveDataOf<String>(null)

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertNoValue()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `zipNonNull3 - all source2 have value - returns mapped value`() {
        val mapper: (String, String, String) -> Unit = mockk()
        every { mapper("1", "2", "3") } returns Unit

        val source1 = liveDataOf<String>("1")
        val source2 = liveDataOf<String>("2")
        val source3 = liveDataOf<String>("3")

        Zip.zipNonNull3(source1, source2, source3, mapper)
            .assertValue(Unit)

        verifySequence {
            mapper("1", "2", "3")
            mapper("1", "2", "3")
            mapper("1", "2", "3")
        }
    }

    // Extension Function Tests

    @Test
    fun `zip top level function - with 2 sources - calls Zip zip2`() {
        val source1: LiveData<Unit> = mockk()
        val source2: LiveData<Unit> = mockk()
        val mapper: (Unit?, Unit?) -> Unit? = mockk()

        every { Zip.zip2(source1, source2, mapper) } returns mockk()

        zip(source1, source2, mapper)

        verify { Zip.zip2(source1, source2, mapper) }
    }

    @Test
    fun `zip top level function - with3 sources - calls Zip zip3`() {
        val source1: LiveData<Unit> = mockk()
        val source2: LiveData<Unit> = mockk()
        val source3: LiveData<Unit> = mockk()
        val mapper: (Unit?, Unit?, Unit?) -> Unit? = mockk()

        every { Zip.zip3(source1, source2, source3, mapper) } returns mockk()

        zip(source1, source2, source3, mapper)

        verify { Zip.zip3(source1, source2, source3, mapper) }
    }

    @Test
    fun `zipNonNull top level function - with 2 sources - calls Zip zipNonNull2`() {
        val source1: LiveData<Unit> = mockk()
        val source2: LiveData<Unit> = mockk()
        val mapper: (Unit, Unit) -> Unit = mockk()

        every { Zip.zipNonNull2(source1, source2, mapper) } returns mockk()

        zipNonNull(source1, source2, mapper)

        verify { Zip.zipNonNull2(source1, source2, mapper) }
    }

    @Test
    fun `zipNonNull top level function - with 3 sources - calls Zip zipNonNull3`() {
        val source1: LiveData<Unit> = mockk()
        val source2: LiveData<Unit> = mockk()
        val source3: LiveData<Unit> = mockk()
        val mapper: (Unit, Unit, Unit) -> Unit = mockk()

        every { Zip.zipNonNull3(source1, source2, source3, mapper) } returns mockk()

        zipNonNull(source1, source2, source3, mapper)

        verify { Zip.zipNonNull3(source1, source2, source3, mapper) }
    }
}