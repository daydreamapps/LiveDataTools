package com.daydreamapplications.livedataextensions

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

/**
 * Zip two LiveData objects together, returning a LiveData that emits a mapped values
 * Mapper will be invoked only when both source LiveData contain a nonull value
 */
fun <S1, S2, T> zipNonNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    mapper: (S1, S2) -> T
): LiveData<T> {


    val mediator = MediatorLiveData<T>()

    fun performMap(value1: S1?, value2: S2?) {
        if (value1 != null && value2 != null) {
            mediator.value = mapper(value1, value2)
        }
    }

    mediator.addSource(source1) { value1 ->
        performMap(value1, source2.value)
    }

    mediator.addSource(source2) { value2 ->
        performMap(source1.value, value2)
    }

    return mediator
}