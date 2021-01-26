package com.daydreamapplications.livedatatools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations

object LogicalOperations {

    /**
     * Return true if the value of all source LiveData is true, else false.
     */
    fun nullableAnd(vararg sources: LiveData<Boolean>): Boolean {
        return NullableLogicalOperations.nullableAnd(*sources.map { it.value }.toTypedArray())
    }

    /**
     * Return true if the value of any source LiveData is true, else false.
     */
    fun nullableOr(vararg sources: LiveData<Boolean>): Boolean {
        return NullableLogicalOperations.nullableOr(*sources.map { it.value }.toTypedArray())
    }

    /**
     * Returns true if the value of all sources are true OR the value of all sources are false, else false.
     */
    fun nullableXAnd(vararg sources: LiveData<Boolean>): Boolean {
        return NullableLogicalOperations.nullableXAnd(*sources.map { it.value }.toTypedArray())
    }

    /**
     * Returns true if the value of any source is true AND the value of value sources are not true, else false.
     */
    fun nullableXOr(vararg sources: LiveData<Boolean>): Boolean {
        return NullableLogicalOperations.nullableXOr(*sources.map { it.value }.toTypedArray())
    }

    /**
     * Return a LiveData that emits a value of True if the emitted values of all sources LiveData is True, else true
     */
    fun and(vararg sources: LiveData<Boolean>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        sources.forEach { source ->
            mediator.addSource(source) {
                mediator.value = nullableAnd(*sources)
            }
        }

        return mediator
    }

    /**
     * Return a LiveData that emits a value of True if the emitted value of any sources LiveData is True, else true
     */
    fun or(vararg sources: LiveData<Boolean>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        sources.forEach { source ->
            mediator.addSource(source) {
                mediator.value = nullableOr(*sources)
            }
        }

        return mediator
    }

    /**
     * Returns a LiveData that emits the result of the applying an XAND (exclusive and) to the value of each source LiveData,
     * triggered by each value emitted by each source LiveData
     *
     * XAND operation returns true if all values are true OR all values are false, else false
     */
    fun xand(vararg sources: LiveData<Boolean>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        sources.forEach { source ->
            mediator.addSource(source) {
                mediator.value = nullableXAnd(*sources)
            }
        }

        return mediator
    }

    /**
     * Return a LiveData that emits a value of True if the emitted value of any sources LiveData is True, else true
     */
    fun xor(vararg sources: LiveData<Boolean>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        sources.forEach { source ->
            mediator.addSource(source) {
                mediator.value = nullableXOr(*sources)
            }
        }

        return mediator
    }
}


/**
 * Return a LiveData that emits a value of True if the emitted values of all sources LiveData is True, else true
 */
fun and(vararg sources: LiveData<Boolean>): LiveData<Boolean> = LogicalOperations.and(*sources)

/**
 * Return a LiveData that emits a value of True if the emitted value of any sources LiveData is True, else true
 */
fun or(vararg sources: LiveData<Boolean>): LiveData<Boolean> = LogicalOperations.or(*sources)

/**
 * Returns a LiveData that emits the result of applying an XAND (exclusive and) to the value of each source LiveData,
 * triggered by each value emitted by each source LiveData
 *
 * XAND returns true if all values are true OR all values are false, else false
 */
fun xand(vararg sources: LiveData<Boolean>): LiveData<Boolean> = LogicalOperations.xand(*sources)

/**
 * Return a LiveData that emits the result of applying an XOR (exclusive or) to the value of each source LiveData,
 * triggered by each value emitted by each source LiveData.
 *
 * XOR returns true if any value is true AND all values are not true, else false.
 */
fun xor(vararg sources: LiveData<Boolean>): LiveData<Boolean> = LogicalOperations.xor(*sources)