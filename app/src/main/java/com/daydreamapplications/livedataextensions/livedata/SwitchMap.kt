package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

object SwitchMap {

    /**
     * Returns a new LiveData by applying a function that you supply to each item emitted by the source
     * LiveData that returns a LiveData, and then emitting the items emitted by the most recently emitted
     * of these LiveData.
     */
    fun <T, R> switchMap(source: LiveData<T>, switchFunction: (T?) -> LiveData<R>): LiveData<R> {
        return Transformations.switchMap(source, switchFunction)
    }

    /**
     * Returns a new LiveData by applying a function that you supply to each non-null item emitted by the source
     * LiveData that returns a LiveData, and then emitting the items emitted by the most recently emitted
     * of these LiveData.
     */
    fun <T, R> switchMapNonNull(source: LiveData<T>, switchFunction: (T) -> LiveData<R>): LiveData<R> {
        return Transformations.switchMap(source) {
            it?.let(switchFunction) ?: emptyLiveData()
        }
    }
}


/**
 * Returns a new LiveData by applying a function that you supply to each item emitted by the source
 * LiveData that returns a LiveData, and then emitting the items emitted by the most recently emitted
 * of these LiveData.
 */
fun <T, R> LiveData<T>.switchMap(switchFunction: (T?) -> LiveData<R>): LiveData<R> {
    return SwitchMap.switchMap(
        this,
        switchFunction
    )
}

/**
 * Returns a new LiveData by applying a function that you supply to each non-null item emitted by the source
 * LiveData that returns a LiveData, and then emitting the items emitted by the most recently emitted
 * of these LiveData.
 */
fun <T, R> LiveData<T>.switchMapNonNull(switchFunction: (T?) -> LiveData<R>): LiveData<R> {
    return SwitchMap.switchMapNonNull(
        this,
        switchFunction
    )
}