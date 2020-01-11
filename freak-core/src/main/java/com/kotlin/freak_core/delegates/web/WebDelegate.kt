package com.kotlin.freak_core.delegates.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import com.kotlin.freak_core.app.ConfigKey
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.delegates.web.route.RouteKeys
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

abstract class WebDelegate : FreakDelegate(), IWebViewInitializer {
    private var isWebViewAvailable: Boolean = false

    var mWebView: WebView? = null
        get() = if (isWebViewAvailable) field else null


    private val WEB_VIEW_QUEUE: ReferenceQueue<WebView> = ReferenceQueue<WebView>()
    var mUrl: String? = null

    var mTopDelegate: FreakDelegate? = null
        get() = if (field == null) {
            mTopDelegate = this
            this
        } else {
            field
        }


    abstract fun setInitializer(): IWebViewInitializer?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        mUrl = args?.getString(RouteKeys.URL.name)
        initWebView()
    }

    @SuppressLint("JavascriptInterface", "AddJavascriptInterface")
    private fun initWebView() {
        if (mWebView != null) {

            mWebView?.removeAllViews()
            mWebView?.destroy()

        } else {
            val initializer = setInitializer()
            if (initializer != null) {
                isWebViewAvailable = true
                val webViewReference: WeakReference<WebView> =
                    WeakReference(WebView(context), WEB_VIEW_QUEUE)
                mWebView = webViewReference.get()
                mWebView = initializer.initWebView(mWebView)
                mWebView?.webViewClient = initializer.initWebViewClient()
                mWebView?.webChromeClient = initializer.initWebViewChromeClient()

                mWebView?.addJavascriptInterface(
                    FreakWebInterface.create(this),
                    Freak.getConfiguration(ConfigKey.JAVASCRIPT_INTERFACE.name) as String
                )
            } else {
                throw NullPointerException("initializer is null")
            }
        }
    }

    fun setTopDelegate(delegate: FreakDelegate) {
        this.mTopDelegate = delegate
    }

    override fun onPause() {
        super.onPause()
        mWebView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mWebView?.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isWebViewAvailable = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView?.removeAllViews()
        mWebView?.destroy()
        mWebView = null
    }
}