package com.kotlin.freak_core.net.interceptors

import androidx.annotation.RawRes
import com.kotlin.freak_core.R

import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.util.file.FileUtil
import okhttp3.*


class DebugInterceptor(
    private val DEBUG_URL: String = "",
    private val DEBUG_RAW_ID: Int = 0
) : BaseInterceptor() {


    companion object {
        const val sort_list_url: String = "sort_list_data"
        val sort_list_data: Int = R.raw.sort_list_data
    }


    override fun onIntercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID)
        } else if (url.contains(sort_list_url)) {
            return debugResponse(chain, sort_list_data)
        }

        return chain.proceed(chain.request())
    }

    private fun getResponse(chain: Interceptor.Chain, json: String): Response {
        return Response.Builder()
            .code(200)
            .addHeader("Content-Type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), json))
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .build()
    }

    private fun debugResponse(chain: Interceptor.Chain, @RawRes idRes: Int): Response {
        val jsonRawStr = FileUtil.readFileFromRaw(Freak.getApplication(), idRes)
//        val jsonRawStr = FileUtil.getFileStremFromAsserts(Freak.getApplication(), "index.json")
        return Response.Builder()
            .code(200)
            .addHeader("Content-Type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), jsonRawStr))
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .build()
    }


}