package com.daydreamapplications.livedataextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Selection of functions to apply filtering operation to LiveData
 */
object Filter {

    /**
     * Filters items emitted by a LiveData by only emitting values which satisfy a specified predicate
     */
    fun <T> filter(source: LiveData<T>, predicate: (T?) -> Boolean): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        mediator.addSource(source) { value ->
            if (predicate(value)) {
                mediator.value = value
            }
        }

        return mediator
    }

    /**
     * Filters items emitted by a LiveData by only emitting non-null values which satisfy a specified predicate
     */
    fun <T> filterNonNull(source: LiveData<T>, predicate: (T) -> Boolean): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        mediator.addSource(source) { value ->
            if (value != null && predicate(value)) {
                mediator.value = value
            }
        }

        return mediator
    }
}

/**
 * Filters items emitted by a LiveData by only emitting values which satisfy a specified predicate
 */
fun <T> LiveData<T>.filter(predicate: (T?) -> Boolean): LiveData<T> {
    return Filter.filter(this, predicate)
}

/**
 * Filters items emitted by a LiveData by only emitting non-null values which satisfy a specified predicate
 */
fun <T> LiveData<T>.filterNonNull(predicate: (T) -> Boolean): LiveData<T> {
    return Filter.filterNonNull(this, predicate)
}