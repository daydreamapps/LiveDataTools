package com.daydreamapplications.livedatatools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import java.util.concurrent.atomic.AtomicBoolean

object DoOn {

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData emits a value
     */
    fun <T> doOnNext(source: LiveData<T>, action: (T?) -> Unit): LiveData<T> {
        return Transformations.map(source) { value ->
            value.also(action)
        }
    }

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData emits a non-null value
     */
    fun <T> doOnNextNonNull(source: LiveData<T>, action: (T) -> Unit): LiveData<T> {
        return Transformations.map(source) { value ->
            value?.also(action)
        }
    }

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData emits the first value
     */
    fun <T> doOnFirst(source: LiveData<T>, action: (T?) -> Unit): LiveData<T> {
        val actionPending = AtomicBoolean(true)

        return Transformations.map(source) { value ->
            if (actionPending.getAndSet(false)) action(value)
            value
        }
    }

    /**
     * Modifies the source LiveData so that it invokes an action when the source LiveData emits the firts non-null
     */
    fun <T> doOnFirstNonNull(source: LiveData<T>, action: (T) -> Unit): LiveData<T> {
        val actionPending = AtomicBoolean(true)

        return Transformations.map(source) { value ->
            if (value != null && actionPending.getAndSet(false)) action(value)
            value
        }
    }
}

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData emits a value
 */
fun <T> LiveData<T>.doOnNext(action: (T?) -> Unit): LiveData<T> = DoOn.doOnNext(this, action)

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData emits a non-null value
 */
fun <T> LiveData<T>.doOnNextNonNull(action: (T) -> Unit): LiveData<T> = DoOn.doOnNextNonNull(this, action)

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData emits the first value
 */
fun <T> LiveData<T>.doOnFirst(action: (T?) -> Unit): LiveData<T> = DoOn.doOnFirst(this, action)

/**
 * Modifies the source LiveData so that it invokes an action when the source LiveData emits the firts non-null
 */
fun <T> LiveData<T>.doOnFirstNonNull(action: (T) -> Unit): LiveData<T> = DoOn.doOnFirstNonNull(this, action)