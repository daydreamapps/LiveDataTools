package com.daydreamapplications.livedataextensions.result.filter

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.filter
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.result.Result

object FilterSuccess {

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Success
     */
    fun <S> filterSuccess(source: LiveData<Result<S>>): LiveData<S> {
        return source.filter { it is Result.Success }
            .map { (it as Result.Success).data }
    }
}

/**
 * Return LiveData that emits only Success values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterSuccess(): LiveData<T> {
    return filter { it is Result.Success }
        .map { (it as Result.Success).data }
}