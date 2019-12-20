package com.kotlin.freak_core.net

import com.kotlin.freak_core.net.callback.*

import okhttp3.RequestBody
import retrofit2.Call
import java.util.*

class RestClient(
    private val URL: String?,
    private val params: WeakHashMap<String, Any>,
    private val SUCCESS: ISuccess?,
    private val ERROR: IError?,
    private val REQUEST: IRequest?,
    private val FAIL: IFail?,
    private val BODY: RequestBody?
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
        when (method.name) {
            HttpMethod.GET.name -> call = service.get(URL, PARAMS)
            HttpMethod.POST.name -> call = service.post(URL, PARAMS)
            HttpMethod.PUT.name -> call = service.put(URL, PARAMS)
            HttpMethod.DELETE.name -> call = service.delete(URL, PARAMS)
        }

        call?.let {
            it.enqueue(getRequestCallback())
        }
    }

    private fun getRequestCallback(): RequestCallbacks {
        return RequestCallbacks(SUCCESS, ERROR, REQUEST, FAIL)
    }

    fun get() {
        request(HttpMethod.GET)

    }

    fun post() {
        request(HttpMethod.POST)
    }

    fun put() {
        request(HttpMethod.PUT)
    }

    fun delete() {
        request(HttpMethod.DELETE)
    }

}