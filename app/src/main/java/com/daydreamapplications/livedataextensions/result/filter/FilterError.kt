package com.daydreamapplications.livedataextensions.result.filter

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.filter
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.result.Result

object FilterError {

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Error
     */
    fun <S> filterError(source: LiveData<Result<S>>): LiveData<Throwable> {
        return source.filter { it is Result.Error }
            .map { (it as Result.Error).exception }
    }
}

/**
 * Return LiveData that emits only Error values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterError(): LiveData<Throwable> {
    return FilterError.filterError(this)
}