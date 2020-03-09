package com.daydreamapplications.livedataextensions.result

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.Bools
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.zip

object IsSuccess {

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Success
     */
    fun <S> isSuccess(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Success }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Success
     */
    fun <S1, S2> isSuccess(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return zip(
            source1.isSuccess(),
            source2.isSuccess(),
            Bools::nullableOr
        )
    }
}

/**
 * Convenience extension function for IsSuccess.isSuccess with a single source
 * @see IsSuccess.isSuccess(LiveData<Result<S>>)
 */
fun <T> LiveData<Result<T>>.isSuccess(): LiveData<Boolean>  =
    IsSuccess.isSuccess(this)

