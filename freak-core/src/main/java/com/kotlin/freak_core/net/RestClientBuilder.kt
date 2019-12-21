package com.kotlin.freak_core.net

import android.content.Context
import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IFail
import com.kotlin.freak_core.net.callback.IRequest
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.ui.FreakLoader
import com.kotlin.freak_core.ui.LoaderStyle
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

class RestClientBuilder {
    private var mUrl: String? = null
    private val PARAMS: WeakHashMap<String, Any> = RestCreator.getParams()
    private var mISuccess: ISuccess? = null
    private var mIError: IError? = null
    private var mIFail: IFail? = null
    private var mIRequest: IRequest? = null
    private var mBody: RequestBody? = null
    private var mLoaderStyle: LoaderStyle? = null
    private var mContext: Context? = null
    constructor()
    constructor(
        mUrl: String?,
        mParams: WeakHashMap<String, Any>?,
        mISuccess: ISuccess?,
        mIError: IError?,
        mIFail: IFail?,
        mIRequest: IRequest?,
        mBody: RequestBody?
    ) {
        this.mUrl = mUrl
        mParams?.let {
            mParams.forEach {
                PARAMS[it.key] = it.value
            }
        }
        this.mISuccess = mISuccess
        this.mIError = mIError
        this.mIFail = mIFail
        this.mIRequest = mIRequest
        this.mBody = mBody
    }


    fun url(url: String): RestClientBuilder {
        this.mUrl = url
        return this
    }

    fun params(params: WeakHashMap<String, Any>): RestClientBuilder {
        params.forEach {
            PARAMS[it.key] = it.value

        }
        return this
    }

    fun params(key: String, value: Any): RestClientBuilder {

        PARAMS[key] = value
        return this
    }

    fun loader(context: Context?, loaderStyle: LoaderStyle): RestClientBuilder {
        this.mContext = context
        this.mLoaderStyle = loaderStyle
        return this
    }

    fun loader(context: Context?): RestClientBuilder {
        this.mContext = context
        this.mLoaderStyle = FreakLoader.DEFAULT_LOADER_STYLE
        return this
    }

    fun raw(raw: String): RestClientBuilder {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw)
        return this
    }

    fun success(iSuccess: ISuccess): RestClientBuilder {
        this.mISuccess = iSuccess
        return this
    }

    fun fail(iFail: IFail): RestClientBuilder {
        this.mIFail = iFail
        return this
    }

    fun error(iError: IError): RestClientBuilder {
        this.mIError = iError
        return this
    }

    fun onRequest(iRequest: IRequest): RestClientBuilder {
        this.mIRequest = iRequest
        return this
    }

    fun build(): RestClient {
        return RestClient(
            mUrl,
            PARAMS,
            mISuccess,
            mIError,
            mIRequest,
            mIFail,
            mBody,
            mLoaderStyle,
            mContext
        )
    }
}