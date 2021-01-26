package com.daydreamapplications.livedatatools.util

object NullableLogicalOperations {

    /**
     * Returns true iff all values are true, else false.
     */
    fun nullableAnd(vararg values: Boolean?): Boolean {
        return if (values.isNotEmpty()) {
            values.all { it == true }
        } else false
    }

    /**
     * Returns true if any value is true, else false.
     */
    fun nullableOr(vararg values: Boolean?): Boolean = values.any { it == true }

    /**
     * Returns true if all values are true OR all values are false, else false.
     */
    fun nullableXAnd(vararg values: Boolean?): Boolean {
        return values.all { it == true } || values.none { it == true }
    }

    /**
     * Returns true if any value is true AND all values are not true, else false.
     */
    fun nullableXOr(vararg values: Boolean?): Boolean {
        return if (values.all { it == true }) {
            return false
        } else values.any { it == true }
    }
}