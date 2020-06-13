package com.daydreamapplications.livedataextensions.livedata.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daydreamapplications.livedataextensions.livedata.map
import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations
import com.daydreamapplications.livedataextensions.util.eraseType

object Success {

    /**
     * Returns a LiveData that emits true when the emitted value of the source LiveData value is Success
     * If the emitted value is not success, false is emitted
     */
    fun <S> isSuccess(source: LiveData<Result<S>>): LiveData<Boolean> = source.map { it is Result.Success }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of any source LiveData is Success
     */
    fun anySuccess(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isSuccess = sources.map { it.value?.isSuccess ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableOr(*isSuccess)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of all source LiveData is Success
     */
    fun allSuccess(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isSuccess = sources.map { it.value?.isSuccess ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableAnd(*isSuccess)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }
}

/**
 * Returns a LiveData that emits true when the emitted value of the source LiveData value is Success
 * If the emitted value is not success, false is emitted
 */
fun <S> LiveData<Result<S>>.isSuccess(): LiveData<Boolean> = Success.isSuccess(this)

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Success
 */
fun <T1, T2> anySuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Success.anySuccess(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Success
 */
fun <T1, T2, T3> anySuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Success
 */
fun <T1, T2, T3, T4> anySuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Success.anySuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Success
 */
fun <T1, T2> allSuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Success.allSuccess(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Success
 */
fun <T1, T2, T3> allSuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Success
 */
fun <T1, T2, T3, T4> allSuccess(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Success.allSuccess(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}
