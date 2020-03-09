package com.daydreamapplications.livedataextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object Zip {

    /**
     * Zip two LiveData objects together, returning a LiveData that emits a mapped values
     * Mapper will be invoked each time either source LiveData emits a value
     */
    fun <S1, S2, T> zip(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        mapper: (S1?, S2?) -> T?
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        mediator.addSource(source1) { value1 ->
            mediator.value = mapper(value1, source2.value)
        }

        mediator.addSource(source2) { value2 ->
            mediator.value = mapper(source1.value, value2)
        }

        return mediator
    }

    /**
     * Zip two LiveData objects together, returning a LiveData that emits a mapped values
     * Mapper will be invoked each time either source LiveData emits a value
     */
    fun <S1, S2, S3, T> zip(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        source3: LiveData<S3>,
        mapper: (S1?, S2?, S3?) -> T?
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        mediator.addSource(source1) { value1 ->
            mediator.value = mapper(value1, source2.value, source3.value)
        }

        mediator.addSource(source2) { value2 ->
            mediator.value = mapper(source1.value, value2, source3.value)
        }

        mediator.addSource(source3) { value3 ->
            mediator.value = mapper(source1.value, source2.value, value3)
        }

        return mediator
    }
}