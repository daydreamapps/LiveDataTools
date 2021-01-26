package com.daydreamapplications.livedatatools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object StartWith {

    /**
     * Return a LiveData that emits a specified item before beginning to emit the items from the source LiveData
     */
    fun <T> startWith(source: LiveData<T>, startItem: T): LiveData<T> {
        return MediatorLiveData<T>().apply {
            postValue(startItem)
            addSource(source, ::postValue)
        }
    }
}

/**
 * Return a LiveData that emits a specified item before beginning to emit the items from the source LiveData
 */
fun <T> LiveData<T>.startWith(startItem: T): LiveData<T> = StartWith.startWith(this, startItem)