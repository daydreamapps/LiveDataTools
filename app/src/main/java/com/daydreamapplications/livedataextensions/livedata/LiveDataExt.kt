package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

/**
 * Executes the operation on each value emitted by live data.
 */
fun <T> LiveData<T>.doOnNext(onNext: (T?) -> Unit): LiveData<T> {
    return Transformations.map(this) { value ->
        value.also(onNext)
    }
}


/**
 * Executes the operation on each nonnull value emitted by live data.
 */
fun <T> LiveData<T>.doOnNextNonNull(onNext: (T) -> Unit): LiveData<T> {
    return Transformations.map(this) { value ->
        value?.also(onNext)
    }
}