package com.daydreamapplications.livedatatools.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daydreamapplications.livedatatools.assertTrue
import com.daydreamapplications.livedatatools.testhelpers.assertTrue
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LogicalOperationsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockkObject(LogicalOperations)
        mockkObject(NullableLogicalOperations)
    }

    @After
    fun tearDown() {
        unmockkObject(LogicalOperations)
        unmockkObject(NullableLogicalOperations)
    }

    // Object functionality tests

    @Test
    fun `nullableAnd - queries live data - returns delegated logical result`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableAnd(true, false) } returns true

        LogicalOperations.nullableAnd(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableAnd(true, false)
        }
    }

    @Test
    fun `nullableOr - queries live data - returns delegated logical result`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableOr(true, false) } returns true

        LogicalOperations.nullableOr(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableOr(true, false)
        }
    }

    @Test
    fun `nullableXAnd - queries live data - returns delegated logical result`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableXAnd(true, false) } returns true

        LogicalOperations.nullableXAnd(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableXAnd(true, false)
        }
    }

    @Test
    fun `nullableXOr - queries live data - returns delegated logical result`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableXOr(true, false) } returns true

        LogicalOperations.nullableXOr(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableXOr(true, false)
        }
    }

    @Test
    fun `and - returns boolean LiveData for sources`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableAnd(true, false) } returns true

        LogicalOperations.and(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableAnd(true, false)
        }
    }

    @Test
    fun `or - returns boolean LiveData for sources`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableOr(true, false) } returns true

        LogicalOperations.or(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableOr(true, false)
        }
    }

    @Test
    fun `xand - returns boolean LiveData for sources`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableXAnd(true, false) } returns true

        LogicalOperations.xand(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableXAnd(true, false)
        }
    }

    @Test
    fun `xor - returns boolean LiveData for sources`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { NullableLogicalOperations.nullableXOr(true, false) } returns true

        LogicalOperations.xor(first, second).assertTrue()

        verify {
            NullableLogicalOperations.nullableXOr(true, false)
        }
    }

    // Public Function Tests

    @Test
    fun `and - external function - delegates to LogicalOperations and`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { LogicalOperations.and(first, second) } returns liveDataOf(true)

        and(first, second).assertTrue()

        verify {
            LogicalOperations.and(first, second)
        }
    }

    @Test
    fun `or - external function - delegates to LogicalOperations or`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { LogicalOperations.or(first, second) } returns liveDataOf(true)

        or(first, second).assertTrue()

        verify {
            LogicalOperations.or(first, second)
        }
    }

    @Test
    fun `xand - external function - delegates to LogicalOperations xand`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { LogicalOperations.xand(first, second) } returns liveDataOf(true)

        xand(first, second).assertTrue()

        verify {
            LogicalOperations.xand(first, second)
        }
    }

    @Test
    fun `xor - external function - delegates to LogicalOperations xor`() {
        val first = liveDataOf(true)
        val second = liveDataOf(false)

        every { LogicalOperations.xor(first, second) } returns liveDataOf(true)

        xor(first, second).assertTrue()

        verify {
            LogicalOperations.xor(first, second)
        }
    }
}