package com.kotlin.freak_core.net.interceptors

import androidx.annotation.RawRes
import com.kotlin.freak_core.R
import com.kotlin.freak_core.app.ConfigKey

import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.util.file.FileUtil
import okhttp3.*


class DebugInterceptor(
    private val DEBUG_URL: String = "",
    private val DEBUG_RAW_ID: Int = 0
) : BaseInterceptor() {


    companion object {

        var apphost: String? = Freak.getConfiguration(ConfigKey.API_HOST.name) as String?
        var sort_list_url: String = apphost + "sort_list_data"
        var sort_list_data: Int = R.raw.sort_list_data

        var sort_content_list_url: String = apphost + "sort_content_list"
        val sort_content_list_data: Int = R.raw.sort_content_data

        var shop_cart_data_url: String = apphost + "shop_cart_data"
        val shop_cart_data: Int = R.raw.shop_cart_data

        var order_list_data_url: String = apphost + "order_list_data"
        val order_list_data: Int = R.raw.order_list_data


        var address_data_url: String = apphost + "address"
        val address_data: Int = R.raw.address_data

        var paging_data_url: String = apphost + "index?index"
        val paging_data: Int = R.raw.index
        val debugMap =
            mapOf(
                sort_list_url to sort_list_data, sort_content_list_url to sort_content_list_data,
                shop_cart_data_url to shop_cart_data, order_list_data_url to order_list_data,
                address_data_url to address_data, paging_data_url to paging_data
            )


    }


    override fun onIntercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID)
        } else if (debugMap.containsKey(url)) {
            return debugResponse(chain, debugMap[url] ?: -1)
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