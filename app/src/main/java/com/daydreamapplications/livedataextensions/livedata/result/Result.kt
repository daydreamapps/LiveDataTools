package com.daydreamapplications.livedataextensions.livedata.result

/**
 * Sealed collection of classes to express the result of an operation
 *
 * Operation in progress -> Result.Loading
 * Operation failed -> Result.Error
 * Operation successful -> Result.Success
 */
sealed class Result<out T> {

    data class Error(val cause: Throwable) : Result<Nothing>() {

        override fun toString(): String {
            return "Result.Error(cause = $cause)"
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

    companion object {

        fun <T> success(data: T): Result<T> = Success(data)

        fun <T> error(exception: Throwable): Result<T> = Error(exception)

        fun <T> loading(): Result<T> = Loading
    }

    /**
     * If instance is a Success object return Success.data, else returns null
     */
    fun getIfSuccessful(): T? = if (this is Success) data else null

    /**
     * Return true if instance is Result.Error
     */
    val isError: Boolean get() = this is Error

    /**
     * Return true if instance is Result.Loading
     */
    val isLoading: Boolean get() = this is Loading

    /**
     * Return true if instance is Result.Success
     */
    val isSuccess: Boolean get() = this is Success
}