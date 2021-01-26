package com.daydreamapplications.livedatatools.livedata.result

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.livedata.doOnNext

object DoOn {

    /**
     * Modifies the source LiveData so that it invokes an action when a Result value in emitted.
     *
     * @param onError acton to perform on Result.Error values
     * @param onSuccess acton to perform on Result.Loading values
     * @param onLoading acton to perform on Result.Success values
     */
    fun <T> doOnNextResult(
        source: LiveData<Result<T>>,
        onError: ((Throwable) -> Unit)? = {},
        onSuccess: ((T) -> Unit)? = {},
        onLoading: (() -> Unit)? = {}
    ): LiveData<Result<T>> {
        return source.doOnNext { result ->
            when (result) {
                is Result.Error -> onError?.invoke(result.cause)
                is Result.Loading -> onLoading?.invoke()
                is Result.Success -> onSuccess?.invoke(result.data)
            }
        }
    }

    /**
     * Modifies the source LiveData so that it invokes an action when a Result.Error value in emitted.
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
     * Modifies the source LiveData so that it invokes an action when a Result.Loading value in emitted.
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
     * Modifies the source LiveData so that it invokes an action when a Result.Success value in emitted.
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
 * Modifies the source LiveData so that it invokes an action when a Result value in emitted.
 *
 * @param onError acton to perform on Result.Error values
 * @param onSuccess acton to perform on Result.Loading values
 * @param onLoading acton to perform on Result.Success values
 */
fun <T> LiveData<Result<T>>.doOnNextResult(
    onError: ((Throwable) -> Unit)? = {},
    onSuccess: ((T) -> Unit)? = {},
    onLoading: (() -> Unit)? = {}
): LiveData<Result<T>> {
    return DoOn.doOnNextResult(
        source = this,
        onError = onError,
        onSuccess = onSuccess,
        onLoading = onLoading
    )
}

/**
 * Modifies the source LiveData so that it invokes an action when a Result.Error value in emitted.
 */
fun <T> LiveData<Result<T>>.doOnError(onError: (Throwable) -> Unit): LiveData<Result<T>> {
    return DoOn.doOnError(source = this, onError = onError)
}

/**
 * Modifies the source LiveData so that it invokes an action when a Result.Loading value in emitted.
 */
fun <T> LiveData<Result<T>>.doOnLoading(onLoading: () -> Unit): LiveData<Result<T>> {
    return DoOn.doOnLoading(source = this, onLoading = onLoading)
}

/**
 * Modifies the source LiveData so that it invokes an action when a Result.Success value in emitted.
 */
fun <T> LiveData<Result<T>>.doOnSuccess(onSuccess: (T) -> Unit): LiveData<Result<T>> {
    return DoOn.doOnSuccess(source = this, onSuccess = onSuccess)
}