package com.kotlin.freak_core.net

import android.content.Context
import com.kotlin.freak_core.net.callback.*
import com.kotlin.freak_core.net.download.DownloadHandler
import com.kotlin.freak_core.ui.FreakLoader
import com.kotlin.freak_core.ui.LoaderStyle
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File
import java.util.*

class RestClient(
    private val URL: String?,
    private val params: WeakHashMap<String, Any>,
    private val SUCCESS: ISuccess?,
    private val ERROR: IError?,
    private val REQUEST: IRequest?,
    private val FAIL: IFail?,
    private val BODY: RequestBody?,
    private val FILE: File?,
    private val LOADER_STYLE: LoaderStyle?,
    private val CONTEXT: Context?,

    private val DOWNLOAD_DIR: String?,
    private val EXTENSION: String?,
    private val NAME: String?

) {

    private var PARAMS: WeakHashMap<String, Any> = RestCreator.ParamsHolder.PARAMS

    init {

        params.forEach {
            PARAMS[it.key] = it.value
        }

    }


    companion object {
        fun builder(): RestClientBuilder {
            return RestClientBuilder()
        }
    }


    fun request(method: HttpMethod) {
        val service: RestService = RestCreator.getRestService()
        var call: Call<String>? = null
        REQUEST?.let {
            REQUEST.onRequestStart()
        }
        LOADER_STYLE?.let {
            FreakLoader.showLoading(CONTEXT, LOADER_STYLE)
        }
        when (method.name) {
            HttpMethod.GET.name -> call = service.get(URL, PARAMS)
            HttpMethod.POST.name -> call = service.post(URL, PARAMS)
            HttpMethod.POST_RAW.name -> call = service.postRaw(URL, BODY)
            HttpMethod.PUT.name -> call = service.put(URL, PARAMS)
            HttpMethod.PUT_RAW.name -> call = service.putRaw(URL, BODY)
            HttpMethod.DELETE.name -> call = service.delete(URL, PARAMS)
            HttpMethod.UPLOAD.name -> {
                FILE?.let {
                    val requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE)
                    val body: MultipartBody.Part =
                        MultipartBody.Part.createFormData("file", FILE.name, requestBody)
                    call = service.upload(URL, body)
                }
            }
        }

        call?.enqueue(getRequestCallback())
    }

    private fun getRequestCallback(): RequestCallbacks {
        return RequestCallbacks(SUCCESS, ERROR, REQUEST, FAIL, LOADER_STYLE)
    }

    fun get() {
        request(HttpMethod.GET)

    }

    fun post() {
        if (BODY == null) {
            request(HttpMethod.POST)
        } else {
            if (!PARAMS.isEmpty()) {
                throw RuntimeException("params must be null when using postRaw body")
            }
            request(HttpMethod.POST_RAW)
        }
    }

    fun put() {
        if (BODY == null) {
            request(HttpMethod.PUT)
        } else {
            if (!PARAMS.isEmpty()) {
                throw RuntimeException("params must be null when using postRaw body")
            }
            request(HttpMethod.PUT_RAW)
        }
    }

    fun delete() {
        request(HttpMethod.DELETE)
    }

    fun upload() {
        request(HttpMethod.UPLOAD)
    }

    fun download() {
        DownloadHandler(
            URL,
            PARAMS,
            SUCCESS,
            ERROR,
            FAIL,
            REQUEST,
            DOWNLOAD_DIR,
            EXTENSION,
            NAME
        ).handleDownload()
    }

}