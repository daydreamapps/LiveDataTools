package com.daydreamapplications.livedataextensions

class Optional<T> private constructor(
    private val value: T?
) {

    companion object {

        fun <T> absent(): Optional<T> {
            return Optional(null)
        }

        fun <T> of(value: T): Optional<T> {
            return Optional(value)
        }

        fun <T> ofNullable(value: T?): Optional<T> {
            return Optional(value)
        }
    }

    fun get(): T {
        return value ?: throw NullPointerException("Get invoked on an absent Optional")
    }

    fun getNullable(): T? = value

    fun isPresent(): Boolean = value != null
}
