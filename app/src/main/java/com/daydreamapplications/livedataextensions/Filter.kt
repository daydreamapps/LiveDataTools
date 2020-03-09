package com.daydreamapplications.livedataextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object Filter {

    /**
     * Passes all values emitted by live data that pass predicate function.
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
     * Passes all nonnull values emitted by live data that pass predicate function.
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
 * Convenience extension function for Filter.filter
 * @see Filter.filter
 */
fun <T> LiveData<T>.filter(predicate: (T?) -> Boolean): LiveData<T> {
    return Filter.filter(this, predicate)
}

/**
 * Convenience extension function for Filter.filterNonNull
 * @see Filter.filterNonNull
 */
fun <T> LiveData<T>.filterNonNull(predicate: (T) -> Boolean): LiveData<T> {
    return Filter.filterNonNull(this, predicate)
}