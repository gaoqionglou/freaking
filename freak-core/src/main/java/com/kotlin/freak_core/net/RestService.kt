package com.kotlin.freak_core.net

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RestService {
    @GET
    fun get(@Url url: String, @QueryMap params: Map<String, Any>): Call<String>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap params: Map<String, Any>): Call<String>

    @FormUrlEncoded
    @PUT
    fun put(@Url url: String, @FieldMap params: Map<String, Any>): Call<String>

    @FormUrlEncoded
    @DELETE
    fun delete(@Url url: String, @FieldMap params: Map<String, Any>): Call<String>

    @Streaming
    @GET
    fun download(@Url url: String, @FieldMap params: Map<String, Any>): Call<ResponseBody>

    @Multipart
    @POST
    fun upload(@Url url: String, @Part file: MultipartBody.Part): Call<String>
}