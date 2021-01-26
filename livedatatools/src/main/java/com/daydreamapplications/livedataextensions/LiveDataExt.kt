package com.daydreamapplications.livedatatools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

/**
 * Emits all values emitted by live data transformed by mapping function
 *
 * @see Transformations
 */
fun <T, R> LiveData<T>.map(mapper: (T?) -> R?): LiveData<R> {
    return Transformations.map(this, mapper)
}

/**
 * Emits all nonnull values emitted by live data transformed by mapping function.
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
 * Returns a LiveData that switches its source to a LiveData provided by the mapper function
 */
fun <T, R> LiveData<T>.switchMap(mapper: (T?) -> LiveData<R>): LiveData<R> {
    return Transformations.switchMap(this, mapper)
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