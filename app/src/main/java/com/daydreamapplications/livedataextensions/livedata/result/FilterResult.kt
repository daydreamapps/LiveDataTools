package com.daydreamapplications.livedataextensions.livedata.result

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.livedata.filter
import com.daydreamapplications.livedataextensions.livedata.map

object FilterResult {

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Error
     */
    fun <S> filterError(source: LiveData<Result<S>>): LiveData<Throwable> {
        return source.filter { it is Result.Error }
            .map { (it as Result.Error).exception }
    }

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Loading
     */
    fun <S> filterLoading(source: LiveData<Result<S>>): LiveData<Result.Loading> {
        return source.filter { it is Result.Loading }
            .map { it as Result.Loading }
    }

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Success
     */
    fun <S> filterSuccess(source: LiveData<Result<S>>): LiveData<S> {
        return source.filter { it is Result.Success }
            .map { (it as Result.Success).data }
    }
}

/**
 * Return LiveData that emits only Error values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterError(): LiveData<Throwable> {
    return FilterResult.filterError(this)
}

/**
 * Return LiveData that emits only Loading values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterLoading(): LiveData<Result.Loading> {
    return FilterResult.filterLoading(this)
}

/**
 * Return LiveData that emits only Success values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterSuccess(): LiveData<T> {
    return FilterResult.filterSuccess(this)
}
