package com.daydreamapplications.livedatatools

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.test(): TestObserver<T> {
    val observer = TestObserver.create<T>()
    observeForever(observer)
    return observer
}

fun <T> LiveData<T>.value(): T? {
    return test().value()
}

fun <T> LiveData<T>.valueHistory(): List<T?> {
    return test().valueHistory()
}

fun <T> LiveData<T>.assertHasValue(): TestObserver<T> {
    return test().assertHasValue()
}

fun <T> LiveData<T>.assertValue(value: T?): TestObserver<T> {
    return test().assertValue(value)
}

fun <T> LiveData<T>.assertValue(valuePredicate: Function<T?, Boolean?>): TestObserver<T> {
    return test().assertValue(valuePredicate)
}

fun <T> LiveData<T>.assertValueIsNull(): TestObserver<T> {
    return test().assertValue(null)
}

fun <T> LiveData<T>.assertValueHistory(vararg values: T): TestObserver<T> {
    return test().assertValueHistory(*values)
}

fun <T> LiveData<T>.assertNoValue(): TestObserver<T> {
    return test().assertNoValue()
}

fun <T> LiveData<T>.assertNever(valuePredicate: Function<T, Boolean>): TestObserver<T> {
    return test().assertNever(valuePredicate)
}
