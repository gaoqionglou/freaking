package com.kotlin.freak_core.net

import com.kotlin.freak_core.ConfigType
import com.kotlin.freak_core.Freak
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RestCreator {


    companion object{
    fun getRestService():RestService{
        return RestServiceHolder.REST_SERVICE
    }

        fun getParams(): WeakHashMap<String, Any> {
            return ParamsHolder.PARAMS
        }
    }


    object ParamsHolder{
        val PARAMS: WeakHashMap<String, Any> = WeakHashMap()
    }

    object RetrofitHolder{
        private val BASE_URL:String= Freak.getConfigurations()[ConfigType.API_HOST.name] as String
        val RETROFIT_CLIENT = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OKHttpHolder.OK_HTTP_CLIENT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()!!
    }

    object OKHttpHolder{
        private const val TIME_OUT=60L
        val OK_HTTP_CLIENT:OkHttpClient = OkHttpClient.Builder().
            connectTimeout(TIME_OUT,TimeUnit.SECONDS)
            .build()
    }

    object RestServiceHolder{
        val REST_SERVICE:RestService = RetrofitHolder.RETROFIT_CLIENT.create(RestService::class.java)

    }



}