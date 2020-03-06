package com.daydreamapplications.livedataextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Returns a Boolean for each emitted value, true if value is Error
 */
fun <T> LiveData<Result<T>>.isError(): LiveData<Boolean> {
    return map { it is Result.Error }
}

/**
 * Returns a Boolean for each emitted value, true if value is Loading
 */
fun <T> LiveData<Result<T>>.isLoading(): LiveData<Boolean> {
    return map { it is Result.Loading }
}

/**
 * Returns a Boolean for each emitted value, true if value is Success
 */
fun <T> LiveData<Result<T>>.isSuccess(): LiveData<Boolean> {
    return map { it is Result.Success<*> }
}

/**
 * Perform an action for each emitted value corresponding to value type
 *
 * Perform onError acton on Error values
 * Perform onSuccess acton on Loading values
 * Perform onLoading acton on Success values
 */
fun <T> LiveData<Result<T>>.doOnNextResult(
    onError: ((Throwable) -> Unit)? = {},
    onSuccess: ((T) -> Unit)? = {},
    onLoading: (() -> Unit)? = {}
): LiveData<Result<T>> {
    return doOnNext { result ->
        when (result) {
            is Result.Error -> onError?.invoke(result.exception)
            is Result.Loading -> onLoading?.invoke()
            is Result.Success -> onSuccess?.invoke(result.data)
        }
    }
}


/**
 * Perform action on each emitted Error value
 */
fun <T> LiveData<Result<T>>.doOnError(onError: (Throwable) -> Unit): LiveData<Result<T>> {
    return doOnNextResult(onError = onError, onSuccess = null, onLoading = null)
}

/**
 * Perform action on each emitted Loading value
 */
fun <T> LiveData<Result<T>>.doOnLoading(onLoading: () -> Unit): LiveData<Result<T>> {
    return doOnNextResult(onError = null, onSuccess = null, onLoading = onLoading)
}

/**
 * Perform action on each emitted Success value
 */
fun <T> LiveData<Result<T>>.doOnSuccess(onSuccess: (T) -> Unit): LiveData<Result<T>> {
    return doOnNextResult(onError = null, onSuccess = onSuccess, onLoading = null)
}

/**
 * Return LiveData that emits only Success values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterSuccess(): LiveData<T> {
    return filter { it is Result.Success }
        .map { (it as Result.Success).data }
}

/**
 * Returns LiveData that emits all non-Error values emitted by the source LiveData
 * Error values are mapped to a Success value using the default
 */
fun <T> LiveData<Result<T>>.defaultIfError(default: T): LiveData<Result<T>> {
    return map { result ->
        if (result is Result.Error) {
            Result.Success(default)
        } else {
            result
        }
    }
}

/**
 * Returns LiveData that emits all non-Error values emitted by the source LiveData
 * Error values are mapped to a Success value by the mapper function
 */
fun <T> LiveData<Result<T>>.ifErrorReturn(mapper: (Throwable) -> T): LiveData<Result<T>> {
    return map { result ->
        if (result is Result.Error) {
            Result.Success(mapper(result.exception))
        } else {
            result
        }
    }
}

/**
 * Returns LiveData that emits all non-Error values emitted by the source LiveData
 * Error values are mapped to a Result value by the mapper function
 */
fun <T> LiveData<Result<T>>.ifErrorReturnResult(mapper: (Throwable) -> Result<T>): LiveData<Result<T>> {
    return map { result ->
        if (result is Result.Error) {
            mapper(result.exception)
        } else {
            result
        }
    }
}


/**
 * Returns LiveData that emits all non-Success values emitted by the source LiveData
 *
 * Success values that pass the predicate are mapped to an Error value using the exceptionProvider
 * Success values that falil the predicate are emitted without change
 */
fun <T> LiveData<Result<T>>.errorIf(
    predicate: (T) -> Boolean,
    exceptionProvider: (T) -> Throwable = { RuntimeException() }
): LiveData<Result<T>> {
    return map { result ->
        if (result is Result.Success && predicate(result.data)) {
            Result.Error(exceptionProvider(result.data))
        } else result
    }
}

/**
 * Return LiveData that emits all Error and Loading values, and only Success values that pass the predicate of the generic type
 */
fun <T> LiveData<Result<T>>.filterResult(predicate: (T) -> Boolean): LiveData<Result<T>> {
    return filter { result ->
        result !is Result.Success || predicate(result.data)
    }
}


/**
 * Return LiveData that emits all Error and Loading values, and mapped Success values of the generic type
 */
fun <S, T> LiveData<Result<S>>.mapResult(mapper: (S) -> T): LiveData<Result<T>> {
    return map { result ->
        when (result) {
            is Result.Success -> Result.Success(mapper(result.data))
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading
        }
    }
}

/**
 * Return LiveData that emits all Error and Loading values
 * All Success values switch to a LiveData provided by the mapping function
 */
fun <S, T> LiveData<Result<S>>.switchMapResult(mapper: (S) -> LiveData<Result<T>>): LiveData<Result<T>> {
    return switchMap { result ->
        when (result) {
            is Result.Success -> mapper(result.data)
            is Result.Error -> liveDataOf<Result<T>>(Result.Error(result.exception))
            else -> liveDataOf<Result<T>>(Result.Loading)
        }
    }
}


/**
 * Zip two LiveData objects together, returning a LiveData that emits a mapped values
 * Mapper will be invoked each time either source LiveData emits a value
 */
fun <S1, S2, T> zipResult(
    source1: LiveData<Result<S1>>,
    source2: LiveData<Result<S2>>,
    mapper: (S1, S2) -> T
): LiveData<Result<T>> {

    val mediator = MediatorLiveData<Result<T>>()

    fun performMap(result1: Result<S1>?, result2: Result<S2>?) {

        val result = when {
            result1 is Result.Success && result2 is Result.Success -> {
                val value = mapper(result1.data, result2.data)
                Result.Success(value)
            }
            result1 is Result.Error -> result1
            result2 is Result.Error -> result2
            else -> Result.Loading
        }

        mediator.value = result
    }


    mediator.addSource(source1) { value1 ->
        performMap(value1, source2.value)
    }

    mediator.addSource(source2) { value2 ->
        performMap(source1.value, value2)
    }

    return mediator
}