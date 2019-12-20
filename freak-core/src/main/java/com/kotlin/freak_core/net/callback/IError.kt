package com.kotlin.freak_core.net.callback

interface IError {
    fun onIError(code: Int, message: String)
}