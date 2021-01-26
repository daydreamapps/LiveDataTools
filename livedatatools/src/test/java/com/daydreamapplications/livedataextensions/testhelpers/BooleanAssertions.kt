package com.daydreamapplications.livedatatools.testhelpers

import org.assertj.core.api.Assertions.assertThat

fun Boolean?.assertTrue() = assertThat(this).isTrue()
fun Boolean?.assertFalse() = assertThat(this).isFalse()
fun Boolean?.assertNull() = assertThat(this).isNull()