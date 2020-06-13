package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

/**
 * Returns a LiveData that applies a specified transform function to each item emitted by the source LiveData and
 * emits the results of these function applications.
 */
fun <T, R> LiveData<T>.map(transform: (T?) -> R?): LiveData<R> {
    return Transformations.map(this, transform)
}
/**
 * Returns a LiveData that applies a specified transform function to each non-null item emitted by the source
 * LiveData and emits the results of these function applications.
 */
fun <T, R> LiveData<T>.mapNonNull(mapper: (T) -> R?): LiveData<R> {

    val mediator = MediatorLiveData<R>()

    mediator.addSource(this) { value ->
        if (value != null) {
            mediator.value = mapper(value)
        }
    }

    return mediator
}

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