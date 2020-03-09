package com.daydreamapplications.livedataextensions.result.state

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.Bools
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.result.Result
import com.daydreamapplications.livedataextensions.zip

object IsError {

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Error
     */
    fun <S> isError(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Error }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Error
     */
    fun <S1, S2> isError(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return zip(
            source1.isError(),
            source2.isError(),
            Bools::nullableOr
        )
    }
}

/**
 * Convenience extension function for IsError.isError with a single source
 * @see IsError.isError(LiveData<Result<S>>)
 */
fun <T> LiveData<Result<T>>.isError(): LiveData<Boolean>  =
    IsError.isError(this)