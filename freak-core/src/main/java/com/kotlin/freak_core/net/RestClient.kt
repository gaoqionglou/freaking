package com.kotlin.freak_core.net

import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IRequest
import com.kotlin.freak_core.net.callback.ISuccess
import okhttp3.RequestBody
import java.util.*

class RestClient {
    private val URL: String?
    private var PARAMS: Map<String, Any> = RestCreator.ParamsHolder.PARAMS as
            private
    val ISUCCESS: ISuccess?
    private val IERROR: IError?
    private val IREQUEST: IRequest?
    private val BODY: RequestBody?

    constructor(
        URL: String?,
        params: WeakHashMap<String, Any>?,
        ISUCCESS: ISuccess?,
        IERROR: IError?,
        IREQUEST: IRequest?,
        BODY: RequestBody?
    ) {
        this.URL = URL
        params?.let {
            params.forEach {
                var str = it.key
                var strr =
                    PARAMS.apply { }
            }
        }
        this.ISUCCESS = ISUCCESS
        this.IERROR = IERROR
        this.IREQUEST = IREQUEST
        this.BODY = BODY
    }


    companion object {
        fun builder(): RestClientBuilder {
            return RestClientBuilder()
        }
    }
}