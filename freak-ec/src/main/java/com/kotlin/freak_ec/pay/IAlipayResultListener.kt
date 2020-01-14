package com.kotlin.freak_ec.pay

interface IAlipayResultListener {
    fun onPaySuccess()
    fun onPaying()
    fun onPayFail()
    fun onPayCancel()
    fun onPayConnectError()
}