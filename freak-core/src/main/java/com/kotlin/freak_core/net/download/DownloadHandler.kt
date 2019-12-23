package com.kotlin.freak_core.net.download

import android.os.AsyncTask
import com.kotlin.freak_core.net.RestCreator
import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IFail
import com.kotlin.freak_core.net.callback.IRequest
import com.kotlin.freak_core.net.callback.ISuccess
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DownloadHandler(
    private var mUrl: String?,
    private val mParams: WeakHashMap<String, Any>,
    private var mISuccess: ISuccess?,
    private var mIError: IError?,
    private var mIFail: IFail?,
    private var mIRequest: IRequest?,
    private var mDir: String?,
    private var mExtension: String?,
    private var mName: String?
) {


    fun handleDownload() {
        mIRequest?.onRequestStart()
        RestCreator.getRestService().download(mUrl, mParams)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val task: SaveFileTask = SaveFileTask(mIRequest, mISuccess)
                        task.executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR,
                            mDir,
                            mExtension,
                            responseBody,
                            mName
                        )
                        if (task.isCancelled) {
                            mIRequest?.onRequestEnd()
                        }

                    } else {
                        mIError?.onIError(response.code(), response.message())
                    }
                }


                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mIFail?.onFail()
                }


            })
    }
}