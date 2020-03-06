package com.daydreamapplications.livedataextensions

/**
 * Sealed collection of classes to express the result of an operation
 *
 * Operation in progress -> Result.Loading
 * Operation failed -> Result.Error
 * Operation successful -> Result.Success
 */
sealed class Result<out T> {

    data class Error(val exception: Throwable) : Result<Nothing>() {

        override fun toString(): String {
            return "Result.Error(exception = $exception)"
        }
    }

    data class Success<out T>(val data: T) : Result<T>() {

        override fun toString(): String {
            return "Result.Success(data = $data)"
        }
    }

    object Loading : Result<Nothing>() {

        override fun toString(): String {
            return "Result.Loading"
        }
    }

    /**
     * If instance is a Success object returns it's data, else returns null
     *
     * @returns Result.Success.data : T, else null
     */
    fun getIfSuccessful(): T? = if (this is Success) data else null

    /**
     * If instance is Error object true
     *
     * @returns Boolean, true if Result.Error
     */
    val isError: Boolean get() = this is Error

    /**
     * If instance is Loading object true
     *
     * @returns Boolean, true if Result.Loading
     */
    val isLoading: Boolean get() = this is Loading

    /**
     * If instance is Success object true
     *
     * @returns Boolean, true if Result.Success
     */
    val isSuccess: Boolean get() = this is Success
}