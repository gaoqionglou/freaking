package com.kotlin.freak_core.net.callback

import android.os.Handler
import com.kotlin.freak_core.ui.loader.FreakLoader
import com.kotlin.freak_core.ui.loader.LoaderStyle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestCallbacks(
    success: ISuccess?,
    error: IError?,
    request: IRequest?,
    fail: IFail?,
    loader_style: LoaderStyle?
) : Callback<String> {

    private val SUCCESS: ISuccess? = success
    private val ERROR: IError? = error
    private val REQUEST: IRequest? = request
    private val FAIL: IFail? = fail
    private val LOADER_STYLE: LoaderStyle? = loader_style

    companion object {
        var hanlder: Handler = Handler()
    }


    override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {

            SUCCESS?.onSuccess(response.body())

        } else {
            ERROR?.onIError(response.code(), response.message())
        }

        LOADER_STYLE?.let {
            hanlder.postDelayed(
                Runnable { FreakLoader.stopLoading() },
                1000L
            )
        }
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        FAIL?.onFail()
        REQUEST?.onRequestEnd()
    }


}