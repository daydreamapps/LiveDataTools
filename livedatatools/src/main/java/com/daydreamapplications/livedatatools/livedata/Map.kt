package com.daydreamapplications.livedatatools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

object Map {

    /**
     * Returns a LiveData that applies a specified transform function to each item emitted by the source LiveData and
     * emits the results of these function applications.
     */
    fun <T, R> map(source: LiveData<T>, transform: (T?) -> R?): LiveData<R> {
        return Transformations.map(source, transform)
    }

    /**
     * Returns a LiveData that applies a specified transform function to each non-null item emitted by the source
     * LiveData and emits the results of these function applications.
     */
    fun <T, R> mapNonNull(source: LiveData<T>, mapper: (T) -> R?): LiveData<R> {

        val mediator = MediatorLiveData<R>()

        mediator.addSource(source) { value ->
            if (value != null) {
                mediator.value = mapper(value)
            }
        }

        return mediator
    }
}

/**
 * Returns a LiveData that applies a specified transform function to each item emitted by the source LiveData and
 * emits the results of these function applications.
 */
fun <T, R> LiveData<T>.map(transform: (T?) -> R?): LiveData<R> = Map.map(this, transform)

/**
 * Returns a LiveData that applies a specified transform function to each non-null item emitted by the source
 * LiveData and emits the results of these function applications.
 */
fun <T, R> LiveData<T>.mapNonNull(transform: (T) -> R?): LiveData<R> = Map.mapNonNull(this, transform)