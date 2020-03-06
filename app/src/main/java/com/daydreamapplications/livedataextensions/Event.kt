package com.daydreamapplications.livedataextensions

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

    override fun equals(other: Any?): Boolean {
        return if (other is Event<*>) {
            hasBeenHandled == other.hasBeenHandled &&
                    content == other.content
        } else false
    }
}