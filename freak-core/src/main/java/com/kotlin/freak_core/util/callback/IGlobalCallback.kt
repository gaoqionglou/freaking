package com.kotlin.freak_core.util.callback

interface IGlobalCallback<T> {
    fun executeCallback(args: T)
}