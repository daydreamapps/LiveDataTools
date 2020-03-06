package com.daydreamapplications.livedataextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class ResultLiveDataExtKtTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `isError - value is Result Error - value is true`() {
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .isError()
            .assertValue(true)

    }

    @Test
    fun `isError - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .isError()
            .assertValue(false)

    }

    @Test
    fun `isError - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .isError()
            .assertValue(false)

    }

    @Test
    fun `isLoading - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .isLoading()
            .assertValue(false)
    }

    @Test
    fun `isLoading - value is Result Loading - value is true`() {
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .isLoading()
            .assertValue(true)
    }

    @Test
    fun `isLoading - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .isLoading()
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .isSuccess()
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .isSuccess()
            .assertValue(false)
    }

    @Test
    fun `isSuccess - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .isSuccess()
            .assertValue(true)
    }


    @Test
    fun `filterSuccess - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .filterSuccess()
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .filterSuccess()
            .assertNoValue()
    }

    @Test
    fun `filterSuccess - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()
        every { value.data } returns Unit

        liveDataOf<Result<Unit>>(value)
            .filterSuccess()
            .assertValue(Unit)

        verifySequence {
            value.data
        }
    }

    @Test
    fun `defaultIfError - value is Result Error - value is false`() {
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .defaultIfError(Unit)
            .assertValue(Result.Success(Unit))
    }

    @Test
    fun `defaultIfError - value is Result Loading - value is false`() {
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .defaultIfError(Unit)
            .assertValue(value)
    }

    @Test
    fun `defaultIfError - value is Result Success - value is false`() {
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .defaultIfError(Unit)
            .assertValue(value)
    }

    @Test
    fun `defaultIfError - value is null - value is null`() {
        liveDataOf<Result<Unit>>(null)
            .defaultIfError(Unit)
            .assertValueIsNull()
    }

    @Test
    fun `ifErrorReturn - value is Result Error - value is false`() {
        val throwable: Throwable = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }
        val mapper: (Throwable) -> Unit = mockk()
        every { mapper(throwable) } returns Unit

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturn(mapper)
            .assertValue(Result.Success(Unit))

        verifySequence {
            value.exception
            mapper(throwable)
        }
    }

    @Test
    fun `ifErrorReturn - value is Result Loading - value is false`() {
        val mapper: (Throwable) -> Unit = mockk()
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturn(mapper)
            .assertValue(value)

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `ifErrorReturn - value is Result Success - value is false`() {
        val mapper: (Throwable) -> Unit = mockk()
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturn(mapper)
            .assertValue(value)

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `ifErrorReturn - value is null - value is null`() {
        val mapper: (Throwable) -> Unit = mockk()

        liveDataOf<Result<Unit>>(null)
            .ifErrorReturn(mapper)
            .assertValueIsNull()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `ifErrorReturnResult - value is Result Error - value is false`() {
        val throwable: Throwable = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }
        val mapper: (Throwable) -> Result<Unit> = mockk()
        val expectedResult: Result<Unit> = mockk()
        every { mapper(throwable) } returns expectedResult

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturnResult(mapper)
            .assertValue(expectedResult)

        verifySequence {
            value.exception
            mapper(throwable)
        }
    }

    @Test
    fun `ifErrorReturnResult - value is Result Loading - value is false`() {
        val mapper: (Throwable) -> Result<Unit> = mockk()
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturnResult(mapper)
            .assertValue(value)

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `ifErrorReturnResult - value is Result Success - value is false`() {
        val mapper: (Throwable) -> Result<Unit> = mockk()
        val value: Result.Success<Unit> = mockk()

        liveDataOf<Result<Unit>>(value)
            .ifErrorReturnResult(mapper)
            .assertValue(value)

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `ifErrorReturnResult - value is null - value is null`() {
        val mapper: (Throwable) -> Result<Unit> = mockk()

        liveDataOf<Result<Unit>>(null)
            .ifErrorReturnResult(mapper)
            .assertValueIsNull()

        verify {
            mapper wasNot Called
        }
    }

    @Test
    fun `errorIf - value is Result Error - value is false`() {
        val predicate: (Unit) -> Boolean = mockk()
        val exceptionProvider: (Unit) -> Throwable = mockk()
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .errorIf(predicate, exceptionProvider)
            .assertValue(value)

        verifySequence {
            predicate wasNot Called
            exceptionProvider wasNot Called
        }
    }

    @Test
    fun `errorIf - value is Result Loading - value is false`() {
        val predicate: (Unit) -> Boolean = mockk()
        val exceptionProvider: (Unit) -> Throwable = mockk()
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .errorIf(predicate, exceptionProvider)
            .assertValue(value)

        verifySequence {
            predicate wasNot Called
            exceptionProvider wasNot Called
        }
    }

    @Test
    fun `errorIf - value is Result Success, predicate returns true - value is Error with exception`() {
        val throwable: Throwable = mockk()
        val predicate: (Unit) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        val exceptionProvider: (Unit) -> Throwable = mockk()
        every { exceptionProvider(Unit) } returns throwable
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        liveDataOf<Result<Unit>>(value)
            .errorIf(predicate, exceptionProvider)
            .assertValue(Result.Error(throwable))

        verify {
            value.data
            predicate(Unit)
            exceptionProvider(Unit)
        }
    }

    @Test
    fun `errorIf - value is Result Success, predicate returns false - value is Success`() {
        val predicate: (Unit) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        val exceptionProvider: (Unit) -> Throwable = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        liveDataOf<Result<Unit>>(value)
            .errorIf(predicate, exceptionProvider)
            .assertValue(value)

        verify {
            value.data
            predicate(Unit)
            exceptionProvider wasNot Called
        }
    }

    @Test
    fun `errorIf - value is null - value is null`() {
        val predicate: (Unit) -> Boolean = mockk()

        liveDataOf<Result<Unit>>(null)
            .errorIf(predicate)
            .assertValueIsNull()

        verify {
            predicate wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has value - no argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()

        emptyLiveData<Result<Unit>>()
            .doOnNextResult(
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            ).test()

        verify {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has null value - no argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()

        liveDataOf<Result<Unit>>(null)
            .doOnNextResult(
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            ).test()

        verify {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has Error value - onError argument is invoked`() {
        val throwable: Throwable = mockk()
        val onError: (Throwable) -> Unit = mockk()
        every { onError(throwable) } just Runs
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        liveDataOf<Result<Unit>>(value)
            .doOnNextResult(
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            ).test()

        verifySequence {
            value.exception
            onError(throwable)
            onSuccess wasNot Called
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnNextResult - liveData has Loading value - onLoading argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        every { onLoading() } just Runs
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .doOnNextResult(
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            ).test()

        verifySequence {
            onError wasNot Called
            onSuccess wasNot Called
            onLoading()
        }
    }

    @Test
    fun `doOnNextResult - liveData has Success value - onSuccess argument is invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val onLoading: () -> Unit = mockk()
        val onSuccess: (Unit) -> Unit = mockk()
        every { onSuccess(Unit) } just Runs
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        liveDataOf<Result<Unit>>(value)
            .doOnNextResult(
                onError = onError,
                onSuccess = onSuccess,
                onLoading = onLoading
            ).test()

        verifySequence {
            value.data
            onError wasNot Called
            onSuccess(Unit)
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnError - liveData has Error value - onError is invoked with exception`() {
        val throwable: Throwable = mockk()
        val onError: (Throwable) -> Unit = mockk()
        every { onError(throwable) } just Runs
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        liveDataOf<Result<Unit>>(value)
            .doOnError(onError)
            .test()

        verifySequence {
            value.exception
            onError(throwable)
        }
    }

    @Test
    fun `doOnError - liveData has Loading value - onError is not invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .doOnError(onError)
            .test()

        verify {
            onError wasNot Called
        }
    }

    @Test
    fun `doOnError - liveData has Success value - onError is not invoked`() {
        val onError: (Throwable) -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        liveDataOf<Result<Unit>>(value)
            .doOnError(onError)
            .test()

        verify {
            onError wasNot Called
        }
    }

    @Test
    fun `doOnLoading - liveData has Error value - onLoading is not invoked`() {
        val throwable: Throwable = mockk()
        val onLoading: () -> Unit = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }

        liveDataOf<Result<Unit>>(value)
            .doOnLoading(onLoading)
            .test()

        verify {
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnLoading - liveData has Loading value - onLoading is invoked`() {
        val onLoading: () -> Unit = mockk()
        val value: Result.Loading = mockk()
        every { onLoading() } just Runs

        liveDataOf<Result<Unit>>(value)
            .doOnLoading(onLoading)
            .test()

        verifySequence {
            onLoading()
        }
    }

    @Test
    fun `doOnLoading - liveData has Success value - onLoading is not invoked`() {
        val onLoading: () -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }

        liveDataOf<Result<Unit>>(value)
            .doOnLoading(onLoading)
            .test()

        verify {
            onLoading wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Error value - onSuccess is not invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Error = mockk()

        liveDataOf<Result<Unit>>(value)
            .doOnSuccess(onSuccess)
            .test()

        verify {
            onSuccess wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Loading value - onSuccess is invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Loading = mockk()

        liveDataOf<Result<Unit>>(value)
            .doOnSuccess(onSuccess)
            .test()

        verify {
            onSuccess wasNot Called
        }
    }

    @Test
    fun `doOnSuccess - liveData has Success value - onSuccess is not invoked`() {
        val onSuccess: (Unit) -> Unit = mockk()
        val value: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        every { onSuccess(Unit) } just Runs

        liveDataOf<Result<Unit>>(value)
            .doOnSuccess(onSuccess)
            .test()

        verifySequence {
            value.data
            onSuccess(Unit)
        }
    }

    @Test
    fun `filterResult - value is Result Error - returns Result Error`() {
        val result: Result.Error = mockk()
        val predicate: (Unit) -> Boolean = mockk()

        liveDataOf<Result<Unit>>(result)
            .filterResult(predicate)
            .assertValue(result)

        verifySequence {
            predicate wasNot Called
        }
    }

    @Test
    fun `filterResult - value is Result Loading - returns Result Loading`() {
        val result: Result.Loading = mockk()
        val predicate: (Unit) -> Boolean = mockk()

        liveDataOf<Result<Unit>>(result)
            .filterResult(predicate)
            .assertValue(result)

        verifySequence {
            predicate wasNot Called
        }
    }

    @Test
    fun `filterResult - value is Result Success, predicate returns true - returns Result Success`() {
        val result: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        val predicate: (Unit) -> Boolean = mockk()
        every { predicate(Unit) } returns true

        liveDataOf<Result<Unit>>(result)
            .filterResult(predicate)
            .assertValue(result)

        verifySequence {
            result.data
            predicate(Unit)
            result == result
        }
    }

    @Test
    fun `filterResult - value is Result Success, predicate returns false - returns no value`() {
        val result: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        val predicate: (Unit) -> Boolean = mockk()
        every { predicate(Unit) } returns false

        liveDataOf<Result<Unit>>(result)
            .filterResult(predicate)
            .assertNoValue()

        verifySequence {
            result.data
            predicate(Unit)
        }
    }

    @Test
    fun `filterResult - value is null - returns Result Loading`() {
        val predicate: (Unit) -> Boolean = mockk()

        liveDataOf<Result<Unit>>(null)
            .filterResult(predicate)
            .assertValueIsNull()

        verifySequence {
            predicate wasNot Called
        }
    }

    @Test
    fun `mapResult - value is Result Error - returns Result Error`() {
        val throwable: Throwable = mockk()
        val result: Result.Error = mockk {
            every { exception } returns throwable
        }
        val mapper: (Unit) -> String = mockk()

        liveDataOf<Result<Unit>>(result)
            .mapResult(mapper)
            .assertValue(Result.Error(throwable))

        verifySequence {
            result.exception
            mapper wasNot Called
        }
    }

    @Test
    fun `mapResult - value is Result Loading - returns Result Loading`() {
        val result: Result.Loading = mockk()
        val mapper: (Unit) -> String = mockk()

        liveDataOf<Result<Unit>>(result)
            .mapResult(mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `mapResult - value is Result Success - returns Result Success`() {
        val result: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        val mapper: (Unit) -> String = mockk()
        every { mapper(Unit) } returns "Mapped value"

        liveDataOf<Result<Unit>>(result)
            .mapResult(mapper)
            .assertValue(Result.Success("Mapped value"))

        verifySequence {
            result.data
            mapper(Unit)
        }
    }

    @Test
    fun `mapResult - value is null - returns Result Loading`() {
        val mapper: (Unit) -> String = mockk()

        liveDataOf<Result<Unit>>(null)
            .mapResult(mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - value is Result Error - returns Result Error`() {
        val throwable: Throwable = mockk()
        val value: Result.Error = mockk {
            every { exception } returns throwable
        }
        val mapper: (Unit) -> LiveData<Result<String>> = mockk()

        liveDataOf<Result<Unit>>(value)
            .switchMapResult(mapper)
            .assertValue(value)

        verifySequence {
            value.exception
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - value is Result Loading - returns Result Loading`() {
        val result: Result.Loading = mockk()
        val mapper: (Unit) -> LiveData<Result<String>> = mockk()

        liveDataOf<Result<Unit>>(result)
            .switchMapResult(mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - value is Result Success - returns value from mapper LiveData`() {
        val result: Result.Success<Unit> = mockk {
            every { data } returns Unit
        }
        val mappedValue : Result<String> = mockk()
        val mapper: (Unit) -> LiveData<Result<String>> = mockk()
        val mappedLiveData =  liveDataOf<Result<String>>(mappedValue)
        every { mapper(Unit) } returns mappedLiveData

        liveDataOf<Result<Unit>>(result)
            .switchMapResult(mapper)
            .assertValue(mappedValue)

        verifySequence {
            result.data
            mapper(Unit)
        }
    }

    @Test
    fun `switchMapResult - value is null - returns Result Loading`() {
        val mapper: (Unit) -> LiveData<Result<String>> = mockk()

        liveDataOf<Result<Unit>>(null)
            .switchMapResult(mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }


//
//    @Test
//    fun `switchMapResult - value is Result Error - returns Result Error`() {
//        val throwable: Throwable = mockk()
//        val value: Result.Error = mockk {
//            every { exception } returns throwable
//        }
//        val mapper: (Unit) -> LiveData<Result<String>> = mockk()
//
//        liveDataOf<Result<Unit>>(value)
//            .switchMapResult(mapper)
//            .assertValue(value)
//
//        verifySequence {
//            value.exception
//            mapper wasNot Called
//        }
//    }

    @Test
    fun `switchMapResult - first is loading, second is empty - returns Result Loading`() {
        val value1 = Result.Loading

        val source1 : LiveData<Result<Unit>> = liveDataOf(value1)
        val source2 : LiveData<Result<Unit>> = emptyLiveData()

        val mapper: (Unit, Unit) -> Unit = mockk()

        zipResult(source1, source2, mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - first is success, second is empty - returns Result Loading`() {
        val value1 : Result.Success<Unit> = mockk()

        val source1 : LiveData<Result<Unit>> = liveDataOf(value1)
        val source2 : LiveData<Result<Unit>> = emptyLiveData()

        val mapper: (Unit, Unit) -> Unit = mockk()

        zipResult(source1, source2, mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - first is null, second is empty - returns Result Loading`() {
        val source1 : LiveData<Result<Unit>> = liveDataOf(null)
        val source2 : LiveData<Result<Unit>> = emptyLiveData()

        val mapper: (Unit, Unit) -> Unit = mockk()

        zipResult(source1, source2, mapper)
            .assertValue(Result.Loading)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - first is error, second is empty - returns Result Error`() {
        val value1 : Result.Error = mockk()

        val source1 : LiveData<Result<Unit>> = liveDataOf(value1)
        val source2 : LiveData<Result<Unit>> = emptyLiveData()

        val mapper: (Unit, Unit) -> Unit = mockk()

        zipResult(source1, source2, mapper)
            .assertValue(value1)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - first is empty, second is error - returns Result Error`() {
        val value2 : Result.Error = mockk()

        val source1 : LiveData<Result<Unit>> = emptyLiveData()
        val source2 : LiveData<Result<Unit>> = liveDataOf(value2)

        val mapper: (Unit, Unit) -> Unit = mockk()

        zipResult(source1, source2, mapper)
            .assertValue(value2)

        verifySequence {
            mapper wasNot Called
        }
    }

    @Test
    fun `switchMapResult - first is success, second is success - returns mapped Result Success`() {
        val value1 = Result.Success("1")
        val value2 = Result.Success("2")

        val source1 : LiveData<Result<String>> = liveDataOf(value1)
        val source2 : LiveData<Result<String>> = liveDataOf(value2)

        val mapper: (String, String) -> Unit = mockk()
        every { mapper("1", "2") } returns Unit

        zipResult(source1, source2, mapper)
            .assertValue(Result.Success(Unit))

        verifySequence {
            mapper("1", "2")
            mapper("1", "2")
        }
    }
}
