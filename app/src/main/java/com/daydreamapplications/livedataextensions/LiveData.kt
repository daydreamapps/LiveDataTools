package com.daydreamapplications.livedataextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * Return a new instance of MutableLiveData
 *
 * @see MutableLiveData
 */
fun <T> emptyMutableLiveData(): MutableLiveData<T> = MutableLiveData()

/**
 * Return instance of MutableLiveData initialised with value
 *
 * @see MutableLiveData
 */
fun <T> mutableLiveDataOf(value: T?): MutableLiveData<T> {
    MutableLiveData(value)
    val liveData = MutableLiveData<T>()
    liveData.postValue(value)
    return liveData
}

/**
 * Return instance of LiveData
 *
 * @see LiveData
 */
fun <T> emptyLiveData(): LiveData<T> = emptyMutableLiveData()

/**
 * Return instance of LiveData initialised with value
 *
 * @see LiveData
 */
fun <T> liveDataOf(value: T?): LiveData<T> = mutableLiveDataOf(value)

/**
 * Returns a LiveData that emits the most recent value from one or more source LiveData
 */
fun <T> flatMap(
    vararg sources: LiveData<T>
): LiveData<T> {

    val mediator = MediatorLiveData<T>()

    sources.forEach { source ->
        mediator.addSource(source, mediator::postValue)
    }

    return mediator
}