package com.daydreamapplications.livedataextensions.livedata.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daydreamapplications.livedataextensions.livedata.map
import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations
import com.daydreamapplications.livedataextensions.util.eraseType

object Error {

    /**
     * Returns a LiveData that emits true when the emitted value of the source LiveData value is Error
     * If the emitted value is not success, false is emitted
     */
    fun <S> isError(source: LiveData<Result<S>>): LiveData<Boolean> = source.map { it is Result.Error }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of any source LiveData is Error
     */
    fun anyError(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isError = sources.map { it.value?.isError ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableOr(*isError)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of all source LiveData is Error
     */
    fun allError(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isError = sources.map { it.value?.isError ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableAnd(*isError)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }
}

/**
 * Returns a LiveData that emits true when the emitted value of the source LiveData value is Error
 * If the emitted value is not success, false is emitted
 */
fun <S> LiveData<Result<S>>.isError(): LiveData<Boolean> = Error.isError(this)

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Error
 */
fun <T1, T2> anyError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Error.anyError(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Error
 */
fun <T1, T2, T3> anyError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Error
 */
fun <T1, T2, T3, T4> anyError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Error.anyError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Error
 */
fun <T1, T2> allError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Error.allError(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Error
 */
fun <T1, T2, T3> allError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Error
 */
fun <T1, T2, T3, T4> allError(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Error.allError(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}
