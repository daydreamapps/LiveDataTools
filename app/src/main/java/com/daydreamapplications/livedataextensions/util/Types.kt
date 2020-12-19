package com.daydreamapplications.livedataextensions.util

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedataextensions.livedata.result.Result


@Suppress("UNCHECKED_CAST")
internal fun <T> LiveData<Result<T>>.eraseType(): LiveData<Result<*>> = this as LiveData<Result<*>>