package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

object DoOnNext {

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData exits a value
     */
    fun <T> doOnNext(source: LiveData<T>, action: (T?) -> Unit): LiveData<T> {
        return Transformations.map(source) { value ->
            value.also(action)
        }
    }

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData exits a non-null value
     */
    fun <T> doOnNextNonNull(source: LiveData<T>, action: (T) -> Unit): LiveData<T> {
        return Transformations.map(source) { value ->
            value?.also(action)
        }
    }
}

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData exits a value
 */
fun <T> LiveData<T>.doOnNext(action: (T?) -> Unit): LiveData<T> = DoOnNext.doOnNext(this, action)

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData exits a non-null value
 */
fun <T> LiveData<T>.doOnNextNonNull(action: (T) -> Unit): LiveData<T> = DoOnNext.doOnNextNonNull(this, action)