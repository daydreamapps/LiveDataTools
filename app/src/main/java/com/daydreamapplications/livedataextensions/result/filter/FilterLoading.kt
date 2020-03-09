package com.daydreamapplications.livedataextensions.result.filter

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.filter
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.result.Result

object FilterLoading {

    /**
     * Return a LiveData instance that emits values emitted by the source LiveData only if the value is Loading
     */
    fun <S> filterLoading(source: LiveData<Result<S>>): LiveData<Result.Loading> {
        return source.filter { it is Result.Loading }
            .map { it as Result.Loading }
    }
}

/**
 * Return LiveData that emits only Loading values emitted by the source LiveData
 */
fun <T> LiveData<Result<T>>.filterLoading(): LiveData<Result.Loading> {
    return FilterLoading.filterLoading(this)
}