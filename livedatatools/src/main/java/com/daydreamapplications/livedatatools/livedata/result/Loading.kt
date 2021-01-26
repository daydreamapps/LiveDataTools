package com.daydreamapplications.livedatatools.livedata.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daydreamapplications.livedatatools.livedata.map
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations
import com.daydreamapplications.livedatatools.util.eraseType

object Loading {

    /**
     * Returns a LiveData that emits true when the emitted value of the source LiveData value is Loading
     * If the emitted value is not success, false is emitted
     */
    fun <S> isLoading(source: LiveData<Result<S>>): LiveData<Boolean> = source.map { it is Result.Loading }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of any source LiveData is Loading
     */
    fun anyLoading(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isLoading = sources.map { it.value?.isLoading ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableOr(*isLoading)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }

    /**
     * Returns a Boolean LiveData that is true if the most recently emitted value of all source LiveData is Loading
     */
    fun allLoading(vararg sources: LiveData<Result<*>>): LiveData<Boolean> {

        val mediator = MediatorLiveData<Boolean>()

        fun evaluate() {
            val isLoading = sources.map { it.value?.isLoading ?: false }.toTypedArray()
            mediator.value = NullableLogicalOperations.nullableAnd(*isLoading)
        }

        sources.forEach {
            mediator.addSource(it) { evaluate() }
        }

        return mediator
    }
}

/**
 * Returns a LiveData that emits true when the emitted value of the source LiveData value is Loading
 * If the emitted value is not success, false is emitted
 */
fun <S> LiveData<Result<S>>.isLoading(): LiveData<Boolean> = Loading.isLoading(this)

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Loading
 */
fun <T1, T2> anyLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Loading.anyLoading(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Loading
 */
fun <T1, T2, T3> anyLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of any source LiveData is Result.Loading
 */
fun <T1, T2, T3, T4> anyLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Loading.anyLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Loading
 */
fun <T1, T2> allLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>
): LiveData<Boolean> {
    return Loading.allLoading(source1.eraseType(), source2.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Loading
 */
fun <T1, T2, T3> allLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>
): LiveData<Boolean> {
    return Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType())
}

/**
 * Returns a Boolean LiveData that emits true if the value of all source LiveData is Result.Loading
 */
fun <T1, T2, T3, T4> allLoading(
    source1: LiveData<Result<T1>>,
    source2: LiveData<Result<T2>>,
    source3: LiveData<Result<T3>>,
    source4: LiveData<Result<T4>>
): LiveData<Boolean> {
    return Loading.allLoading(source1.eraseType(), source2.eraseType(), source3.eraseType(), source4.eraseType())
}
