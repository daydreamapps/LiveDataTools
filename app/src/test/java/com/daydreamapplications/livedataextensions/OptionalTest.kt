package com.daydreamapplications.livedataextensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class OptionalTest {


    @Test
    fun `absent - returns Optional with null value`() {
        val optional = Optional.absent<Unit>()

        assertThat(optional.isPresent()).isFalse()
        assertThat(optional.getNullable()).isNull()
    }

    @Test
    fun `of - returns Optional with nonnull value`() {
        val optional = Optional.of(Unit)

        assertThat(optional.isPresent()).isTrue()
        assertThat(optional.getNullable()).isEqualTo(Unit)
    }

    @Test
    fun `ofNullable - returns Optional with null value`() {
        val optional = Optional.ofNullable<Unit>(null)

        assertThat(optional.isPresent()).isFalse()
        assertThat(optional.getNullable()).isNull()
    }

    @Test
    fun `ofNullable - returns Optional with nonnull value`() {
        val optional = Optional.ofNullable(Unit)

        assertThat(optional.isPresent()).isTrue()
        assertThat(optional.getNullable()).isEqualTo(Unit)
    }

    @Test
    fun `isPresent - value is null - returns false`() {
        val isPresent = Optional.absent<Unit>().isPresent()

        assertThat(isPresent).isFalse()
    }

    @Test
    fun `isPresent - value is nonnull - returns true`() {
        val isPresent = Optional.of(Unit).isPresent()

        assertThat(isPresent).isTrue()
    }

    @Test
    fun `get - value is nonnull - returns value`() {
        val value = Optional.of(Unit).get()

        assertThat(value).isEqualTo(Unit)
    }

    @Test(expected = NullPointerException::class)
    fun `get - value is null - get throws NullPointerException`() {
        Optional.absent<Unit>().get()
    }

    @Test
    fun `getNullable - value is null - returns value`() {
        val value = Optional.absent<Unit>().getNullable()

        assertThat(value).isNull()
    }

    @Test
    fun `getNullable - value is nonnull - returns value`() {
        val value = Optional.of(Unit).getNullable()

        assertThat(value).isEqualTo(Unit)
    }
}