package com.daydreamapplications.livedataextensions.result.state

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.Bools
import com.daydreamapplications.livedataextensions.Zip
import com.daydreamapplications.livedataextensions.map
import com.daydreamapplications.livedataextensions.result.Result

object IsLoading {

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Loading
     */
    fun <S> isLoading(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Loading }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Loading
     */
    fun <S1, S2> isLoading(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return Zip.zip(
            source1.isLoading(),
            source2.isLoading(),
            Bools::nullableOr
        )
    }
}

/**
 * Convenience extension function for IsLoading.isLoading with a single source
 * @see IsLoading.isLoading(LiveData<Result<S>>)
 */
fun <T> LiveData<Result<T>>.isLoading(): LiveData<Boolean> =
    IsLoading.isLoading(
        this
    )