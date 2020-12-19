package com.daydreamapplications.livedataextensions.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Object containing functionality to zip the values of LiveData objects
 *
 * All zip functions work in the same manner:
 *
 * A number of source (upstream) LiveData and a mapping function are provided, a (downstream) LiveData is returned.
 *
 * When a source LiveData emits a value the current values of all sources are acquired and passed to
 * the mapper function, posting the result to the returned (downstream) LiveData.
 *
 * Note: No operation will take place until the returned (downstream) LiveData has an observer.
 *
 * @See androidx.lifecycle.Observer
 * @See androidx.lifecycle.LiveData
 */
object Zip {

    /**
     * Zip two LiveData objects together, returning a LiveData that emits a mapped values
     * Mapper will be invoked each time either source LiveData emits a value
     */
    fun <S1, S2, T> zip2(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        mapper: (S1?, S2?) -> T?
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        fun evaluate() {
            val value1 = source1.value
            val value2 = source2.value

            mediator.value = mapper(value1, value2)
        }

        mediator.addSource(source1) { evaluate() }
        mediator.addSource(source2) { evaluate() }

        return mediator
    }

    /**
     * Zip two LiveData objects together, returning a LiveData that emits a mapped values
     * Mapper will be invoked each time either source LiveData emits a value
     */
    fun <S1, S2, S3, T> zip3(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        source3: LiveData<S3>,
        mapper: (S1?, S2?, S3?) -> T?
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()


        fun evaluate() {
            val value1 = source1.value
            val value2 = source2.value
            val value3 = source3.value

            mediator.value = mapper(value1, value2, value3)
        }

        mediator.addSource(source1) { evaluate() }
        mediator.addSource(source2) { evaluate() }
        mediator.addSource(source3) { evaluate() }

        return mediator
    }

    /**
     * Zip two LiveData objects together, returning a LiveData that emits a mapped values
     * Mapper will be invoked only when both source LiveData contain a nonull value
     */
    fun <S1, S2, T> zipNonNull2(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        mapper: (S1, S2) -> T
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        fun evaluate() {
            val value1: S1? = source1.value
            val value2: S2? = source2.value

            if (value1 != null && value2 != null) {
                mediator.value = mapper(value1, value2)
            }
        }

        mediator.addSource(source1) { evaluate() }
        mediator.addSource(source2) { evaluate() }

        return mediator
    }

    /**
     * Zip three LiveData objects together, using a three-way non-null mapping function
     *
     * When a source LiveData emits a value it will trigger an evaluation, by calling `getValue()` of each LiveData,
     * if all values are non-null the mapper function will be invoked and the result emitted downstream.
     *
     * @See LiveData.getValue
     */
    fun <S1, S2, S3, T> zipNonNull3(
        source1: LiveData<S1>,
        source2: LiveData<S2>,
        source3: LiveData<S3>,
        mapper: (S1, S2, S3) -> T
    ): LiveData<T> {

        val mediator = MediatorLiveData<T>()

        fun evaluate() {
            val value1: S1? = source1.value
            val value2: S2? = source2.value
            val value3: S3? = source3.value

            if (value1 != null && value2 != null && value3 != null) {
                mediator.value = mapper(value1, value2, value3)
            }
        }

        mediator.addSource(source1) { evaluate() }
        mediator.addSource(source2) { evaluate() }
        mediator.addSource(source3) { evaluate() }

        return mediator
    }
}

/**
 * Convenience top-level function to calls Zip.zip2
 * @see Zip.zip2
 */
fun <S1, S2, T> zip(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    mapper: (S1?, S2?) -> T?
): LiveData<T> {

    return Zip.zip2(
        source1,
        source2,
        mapper
    )
}


/**
 * Convenience top-level function to calls Zip.zip
 * @see Zip.zip3
 */
fun <S1, S2, S3, T> zip(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    mapper: (S1?, S2?, S3?) -> T?
): LiveData<T> {

    return Zip.zip3(
        source1,
        source2,
        source3,
        mapper
    )
}

/**
 * Convenience top-level function to calls Zip.zipNonNull2
 * @see Zip.zipNonNull2
 */
fun <S1, S2, T> zipNonNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    mapper: (S1, S2) -> T
): LiveData<T> {

    return Zip.zipNonNull2(
        source1,
        source2,
        mapper
    )
}


/**
 * Convenience top-level function to calls Zip.zipNonNull3
 * @see Zip.zipNonNull3
 */
fun <S1, S2, S3, T> zipNonNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    mapper: (S1, S2, S3) -> T
): LiveData<T> {

    return Zip.zipNonNull3(
        source1,
        source2,
        source3,
        mapper
    )
}