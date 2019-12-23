package com.kotlin.freak_core.net.interceptors

import android.content.Context
import androidx.annotation.RawRes
import com.kotlin.freak_core.Freak
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import okhttp3.*
import java.io.BufferedReader
import java.io.InputStreamReader


class DebugInterceptor(
    private val DEBUG_URL: String = "",
    private val DEBUG_RAW_ID: Int = 0
) : BaseInterceptor() {


    override fun onIntercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID)
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
        val jsonRawStr = readFileFromRaw(Freak.getApplication(), idRes)
        return Response.Builder()
            .code(200)
            .addHeader("Content-Type", "application/json")
            .body(ResponseBody.create(MediaType.parse("application/json"), jsonRawStr))
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .build()
    }


    /**
     * 从raw包下读取数据
     * @param context
     * @param rawName   R.raw.jx
     * @return
     */
    fun readFileFromRaw(context: Context, rawName: Int): String {
        try {
            val inputReader = InputStreamReader(context.resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String?
            var result = ""

            do {
                line = bufReader.readLine()
                result += line
            } while (line != null)
//            while ((line = bufReader.readLine()) != null)
//                result += line
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * @param activity
     * @param fileName  main.json
     * @return
     */
    private fun getFileStremFromAsserts(context: Context, fileName: String): String {

        try {
            //从assets获取json文件
            val isr =
                InputStreamReader(context.assets.open(fileName))
            //字节流转字符流
            val bfr = BufferedReader(isr)
            var line: String?
            val stringBuilder = StringBuilder()
//            while ((line = bfr.readLine()) != null) {
//                stringBuilder.append(line)
//            }//将JSON数据转化为字符串
            do {
                line = bfr.readLine()
                stringBuilder.append(line)
            } while (line != null)
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

}