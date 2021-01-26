package com.daydreamapplications.livedatatools.result

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.Bools
import com.daydreamapplications.livedatatools.Zip
import com.daydreamapplications.livedatatools.map

object StateResult {

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Error
     */
    fun <S> isError1(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Error }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Error
     */
    fun <S1, S2> isError2(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return Zip.zip2(
            source1.isError(),
            source2.isError(),
            Bools::nullableOr
        )
    }

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Loading
     */
    fun <S> isLoading1(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Loading }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Loading
     */
    fun <S1, S2> isLoading2(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return Zip.zip2(
            source1.isLoading(),
            source2.isLoading(),
            Bools::nullableOr
        )
    }

    /**
     * Returns a Boolean LiveData that is true if the emitted source value is Success
     */
    fun <S> isSuccess1(
        source: LiveData<Result<S>>
    ): LiveData<Boolean> {
        return source.map { it is Result.Success }
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value, any either source, is Success
     */
    fun <S1, S2> isSuccess2(
        source1: LiveData<Result<S1>>,
        source2: LiveData<Result<S2>>
    ): LiveData<Boolean> {
        return Zip.zip2(
            source1.isSuccess(),
            source2.isSuccess(),
            Bools::nullableOr
        )
    }
}

/**
 * Convenience extension function StateResult.isError1
 * @see StateResult.isError1
 */
fun <T> LiveData<Result<T>>.isError(): LiveData<Boolean> {
    return StateResult.isError1(this)
}

/**
 * Convenience extension function for StateResult.isLoading1
 * @see StateResult.isLoading1
 */
fun <T> LiveData<Result<T>>.isLoading(): LiveData<Boolean> {
    return StateResult.isLoading1(this)
}

/**
 * Convenience extension function for StateResult.isSuccess1
 * @see StateResult.isSuccess1
 */
fun <T> LiveData<Result<T>>.isSuccess(): LiveData<Boolean> {
    return StateResult.isSuccess1(this)
}
