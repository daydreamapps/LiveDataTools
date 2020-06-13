package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

object Take {

    /**
     * Returns a LiveData  that emits only the first {@code count} items emitted by the source LiveData. If the source emits fewer than
     * {@code count} items then all of its items are emitted.
     */
    fun <T> take(source: LiveData<T>, count: Long): LiveData<T> {
        val ticker = AtomicLong(0)

        val mediator = MediatorLiveData<T>()
        mediator.addSource(source) { sourceValue ->

            if (ticker.getAndIncrement() < count) {
                mediator.value = sourceValue
            }
        }

        return mediator
    }

    /**
     * Returns a LiveData  that emits only the first {@code count} non-null items emitted by the source LiveData.
     * If the source emits fewer than {@code count} non-null items then all of its items are emitted.
     */
    fun <T> takeNonNull(source: LiveData<T>, count: Long): LiveData<T> {
        val ticker = AtomicLong(0)

        val mediator = MediatorLiveData<T>()
        mediator.addSource(source) { sourceValue ->

            if (sourceValue != null && ticker.getAndIncrement() < count) {
                mediator.value = sourceValue
            }
        }

        return mediator
    }

    /**
     * Returns a LiveData that emits only the first item emitted by the source LiveData.
     */
    fun <T> takeFirst(source: LiveData<T>): LiveData<T> = take(source, 1)

    /**
     * Returns a LiveData that emits only the first non-null item emitted by the source LiveData.
     */
    fun <T> takeFirstNonNull(source: LiveData<T>): LiveData<T> = takeNonNull(source, 1)

    /**
     * Returns a LiveData that emits items emitted by the source LiveData so long as each item and satisfies a
     * specified condition, and emits no further items once the condition is not satisfied.
     */
    fun <T> takeWhile(source: LiveData<T>, predicate: (T?) -> Boolean): LiveData<T> {
        val shouldContinue = AtomicBoolean(true)

        val mediator = MediatorLiveData<T>()
        mediator.addSource(source) { sourceValue ->

            val acceptValue = predicate(sourceValue)

            if (shouldContinue.get() && acceptValue) {
                mediator.value = sourceValue
            } else if(!acceptValue) {
                shouldContinue.set(false)
            }
        }

        return mediator
    }

    /**
     * Returns a LiveData that emits non-null items emitted by the source LiveData so long as each item and satisfies a
     * specified condition, and emits no further items once the condition is not satisfied.
     *
     * Null values emitted by the source LiveData are not emitted, and are ignored by the predicate.
     */
    fun <T> takeWhileNonNull(source: LiveData<T>, predicate: (T) -> Boolean): LiveData<T> {
        val shouldContinue = AtomicBoolean(true)

        val mediator = MediatorLiveData<T>()
        mediator.addSource(source) { sourceValue ->

            val acceptValue = predicate(sourceValue)

            if (sourceValue != null && shouldContinue.get() && acceptValue) {
                mediator.value = sourceValue
            } else if (!acceptValue) {
                shouldContinue.set(false)
            }
        }

        return mediator
    }
}

/**
 * Returns a LiveData  that emits only the first {@code count} items emitted by the source LiveData. If the source emits fewer than
 * {@code count} items then all of its items are emitted.
 */
fun <T> LiveData<T>.take(count: Long): LiveData<T> = Take.take(this, count)

/**
 * Returns a LiveData  that emits only the first {@code count} non-null items emitted by the source LiveData.
 * If the source emits fewer than {@code count} non-null items then all of its items are emitted.
 */
fun <T> LiveData<T>.takeNonNull(count: Long): LiveData<T> = Take.takeNonNull(this, count)

/**
 * Returns a LiveData that emits only the first item emitted by the source LiveData.
 */
fun <T> LiveData<T>.takeFirst(): LiveData<T> = Take.takeFirst(this)

/**
 * Returns a LiveData that emits only the first non-null item emitted by the source LiveData.
 */
fun <T> LiveData<T>.takeFirstNonNull(): LiveData<T> = Take.takeFirstNonNull(this)

/**
 * Returns a LiveData that emits items emitted by the source LiveData so long as each item and satisfies a
 * specified condition, and emits no further items once the condition is not satisfied.
 */
fun <T> LiveData<T>.takeWhile(predicate: (T?) -> Boolean): LiveData<T> = Take.takeWhile(this, predicate)

/**
 * Returns a LiveData that emits non-null items emitted by the source LiveData so long as each item and satisfies a
 * specified condition, and emits no further items once the condition is not satisfied.
 *
 * Null values emitted by the source LiveData are not emitted, and are ignored by the predicate.
 */
fun <T> LiveData<T>.takeWhileNonNull(predicate: (T) -> Boolean): LiveData<T> = Take.takeWhileNonNull(this, predicate)