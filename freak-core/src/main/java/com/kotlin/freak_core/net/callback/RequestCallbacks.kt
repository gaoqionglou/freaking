package com.kotlin.freak_core.net.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestCallbacks : Callback<String> {

    private val SUCCESS: ISuccess?
    private val ERROR: IError?
    private val REQUEST: IRequest?
    private val FAIL: IFail?

    constructor(SUCCESS: ISuccess?, ERROR: IError?, REQUEST: IRequest?, FAIL: IFail?) {
        this.SUCCESS = SUCCESS
        this.ERROR = ERROR
        this.REQUEST = REQUEST
        this.FAIL = FAIL
    }


    override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {

            SUCCESS?.onSuccess(response.body())

        } else {
            ERROR?.onIError(response.code(), response.message())
        }
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        FAIL?.onFail()
        REQUEST?.onRequestEnd()
    }


}