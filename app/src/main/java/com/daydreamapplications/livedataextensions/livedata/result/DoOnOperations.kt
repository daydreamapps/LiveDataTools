package com.daydreamapplications.livedataextensions.livedata.result

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.livedata.doOnNext

object DoOnOperations {

    /**
     * Perform an action for each emitted value corresponding to value type
     *
     * Perform onError acton on Error values
     * Perform onSuccess acton on Loading values
     * Perform onLoading acton on Success values
     */
    fun <T> doOnNextResult(
        source: LiveData<Result<T>>,
        onError: ((Throwable) -> Unit)? = {},
        onSuccess: ((T) -> Unit)? = {},
        onLoading: (() -> Unit)? = {}
    ): LiveData<Result<T>> {
        return source.doOnNext { result ->
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
    fun <T> doOnError(
        source: LiveData<Result<T>>,
        onError: (Throwable) -> Unit
    ): LiveData<Result<T>> {
        return doOnNextResult(
            source = source,
            onError = onError,
            onSuccess = null,
            onLoading = null
        )
    }

    /**
     * Perform action on each emitted Loading value
     */
    fun <T> doOnLoading(source: LiveData<Result<T>>, onLoading: () -> Unit): LiveData<Result<T>> {
        return doOnNextResult(
            source = source,
            onError = null,
            onSuccess = null,
            onLoading = onLoading
        )
    }

    /**
     * Perform action on each emitted Success value
     */
    fun <T> doOnSuccess(source: LiveData<Result<T>>, onSuccess: (T) -> Unit): LiveData<Result<T>> {
        return doOnNextResult(
            source = source,
            onError = null,
            onSuccess = onSuccess,
            onLoading = null
        )
    }
}


/**
 * Convenience extension function for DoOnOperations.doOnNextResult
 * @see DoOnOperations.doOnNextResult
 */
fun <T> LiveData<Result<T>>.doOnNextResult(
    onError: ((Throwable) -> Unit)? = {},
    onSuccess: ((T) -> Unit)? = {},
    onLoading: (() -> Unit)? = {}
): LiveData<Result<T>> {
    return DoOnOperations.doOnNextResult(
        source = this,
        onError = onError,
        onSuccess = onSuccess,
        onLoading = onLoading
    )
}

/**
 * Convenience extension function for DoOnOperations.doOnNextResult
 * @see DoOnOperations.doOnError
 */
fun <T> LiveData<Result<T>>.doOnError(onError: (Throwable) -> Unit): LiveData<Result<T>> {
    return DoOnOperations.doOnError(source = this, onError = onError)
}

/**
 * Convenience extension function for DoOnOperations.doOnNextResult
 * @see DoOnOperations.doOnLoading
 */
fun <T> LiveData<Result<T>>.doOnLoading(onLoading: () -> Unit): LiveData<Result<T>> {
    return DoOnOperations.doOnLoading(source = this, onLoading = onLoading)
}

/**
 * Convenience extension function for DoOnOperations.doOnNextResult
 * @see DoOnOperations.doOnSuccess
 */
fun <T> LiveData<Result<T>>.doOnSuccess(onSuccess: (T) -> Unit): LiveData<Result<T>> {
    return DoOnOperations.doOnSuccess(source = this, onSuccess = onSuccess)
}