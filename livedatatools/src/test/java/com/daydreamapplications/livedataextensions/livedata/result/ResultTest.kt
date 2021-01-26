package com.daydreamapplications.livedatatools.livedata.result

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ResultTest {

    @Test
    fun getIfSuccessful() {
    }

    @Test
    fun `isError - Result Error - returns false`() {
        val result = Result.Error(mockk())

        assertThat(result.isError).isTrue()
    }

    @Test
    fun `isError - Result Loading - returns false`() {
        val result = Result.Loading

        assertThat(result.isError).isFalse()
    }

    @Test
    fun `isError - Result Success - returns false`() {
        val result = Result.Success(Unit)

        assertThat(result.isError).isFalse()
    }

    @Test
    fun `isLoading - Result Error - returns false`() {
        val result = Result.Error(mockk())

        assertThat(result.isLoading).isFalse()
    }

    @Test
    fun `isLoading - Result Loading - returns false`() {
        val result = Result.Loading

        assertThat(result.isLoading).isTrue()
    }

    @Test
    fun `isLoading - Result Success - returns false`() {
        val result = Result.Success(Unit)

        assertThat(result.isLoading).isFalse()
    }

    @Test
    fun `isSuccess - Result Error - returns false`() {
        val result = Result.Error(mockk())

        assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `isSuccess - Result Loading - returns false`() {
        val result = Result.Loading

        assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `isSuccess - Result Success - returns false`() {
        val result = Result.Success(Unit)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `ifSuccessful - Result Error - returns false`() {
        val result : Result<Unit> = Result.Error(mockk())

        assertThat(result.getIfSuccessful()).isNull()
    }

    @Test
    fun `ifSuccessful - Result Loading - returns false`() {
        val result : Result<Unit> = Result.Loading

        assertThat(result.getIfSuccessful()).isNull()
    }

    @Test
    fun `ifSuccessful - Result Success - returns false`() {
        val result : Result<Unit> = Result.Success(Unit)

        assertThat(result.getIfSuccessful()).isEqualTo(Unit)
    }
}