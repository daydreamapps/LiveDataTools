package com.daydreamapplications.livedatatools.util

import androidx.lifecycle.LiveData
import com.daydreamapplications.livedatatools.livedata.result.Result


@Suppress("UNCHECKED_CAST")
internal fun <T> LiveData<Result<T>>.eraseType(): LiveData<Result<*>> = this as LiveData<Result<*>>