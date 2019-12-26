package com.kotlin.freak_core.net

import com.kotlin.freak_core.app.ConfigKey
import com.kotlin.freak_core.app.Freak
import okhttp3.Interceptor
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
        private val BASE_URL: String = Freak.getConfigurations()[ConfigKey.API_HOST.name] as String
        val RETROFIT_CLIENT = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OKHttpHolder.OK_HTTP_CLIENT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()!!
    }

    object OKHttpHolder{
        private const val TIME_OUT=60L
        private val INTERCEPTORS = Freak.getConfiguration(ConfigKey.INTERCEPTORS) as ArrayList<*>
        private val BUILDER = OkHttpClient.Builder()

        private fun addInterceptor(): OkHttpClient.Builder {
            INTERCEPTORS.forEach {
                BUILDER.addInterceptor(it as Interceptor)
            }
            return BUILDER
        }

        val OK_HTTP_CLIENT: OkHttpClient = addInterceptor().
            connectTimeout(TIME_OUT,TimeUnit.SECONDS)
            .build()
    }

    object RestServiceHolder{
        val REST_SERVICE:RestService = RetrofitHolder.RETROFIT_CLIENT.create(RestService::class.java)

    }



}