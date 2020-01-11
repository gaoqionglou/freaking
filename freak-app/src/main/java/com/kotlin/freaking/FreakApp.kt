package com.kotlin.freaking

import FontEcModule
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.delegates.web.event.Event
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_ec.database.DataBaseManager

class FreakApp : Application() {
    val TAG = "FreakApp";

    init {
        Log.i(TAG, " app init")
    }

    override fun onCreate() {
        super.onCreate()
        Freak.init(this)
            .withApiHost("https://127.0.0.1/")
            .withIcon(FontEcModule())
            .withIcon(FontAwesomeModule())
            .withInterceptor(DebugInterceptor("index", R.raw.index))
            .withWebEvent("test", TestEvent())
            .withJavaScriptInterface("Freak")
            .configure()
        DataBaseManager.init(this)
        Stetho.initializeWithDefaults(this)
    }
}


class TestEvent : Event() {
    override fun execute(params: String): String {
        Toast.makeText(mContext, params, Toast.LENGTH_SHORT).show()
        if (mAction.equals("test")) {
            mWebView?.post(object : Runnable {
                override fun run() {
                    mWebView?.evaluateJavascript("nativeCall();", null)
                }

            })
        }
        return ""
    }

}