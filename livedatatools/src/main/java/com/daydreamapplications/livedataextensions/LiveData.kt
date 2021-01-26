package com.daydreamapplications.livedatatools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> emptyMutableLiveData(): MutableLiveData<T> = MutableLiveData()

fun <T> mutableLiveDataOf(value: T?): MutableLiveData<T> {
    val liveData = MutableLiveData<T>()
    liveData.postValue(value)
    return liveData
}


fun <T> emptyLiveData(): LiveData<T> = emptyMutableLiveData()

fun <T> liveDataOf(value: T?): LiveData<T> {
    return mutableLiveDataOf(value)
}

/**
 * Flatten a number of LiveData objects of the same type into a single LiveData
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