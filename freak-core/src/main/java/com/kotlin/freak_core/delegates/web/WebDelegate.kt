package com.kotlin.freak_core.delegates.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.delegates.web.route.RouteKeys
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

abstract class WebDelegate : FreakDelegate() {
    private var isWebViewAvailable: Boolean = false

    var mWebView: WebView? = null
        get() = if (isWebViewAvailable) field else null


    private val WEB_VIEW_QUEUE: ReferenceQueue<WebView> = ReferenceQueue()
    var mUrl: String? = null


    abstract fun setInitializer(): IWebViewInitializer?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        mUrl = args?.getString(RouteKeys.URL.name)

    }

    @SuppressLint("JavascriptInterface", "AddJavascriptInterface")
    private fun initWebView() {
        if (mWebView != null) {
            mWebView.run {
                this?.removeAllViews()
                this?.destroy()
            }
        } else {
            val initializer = setInitializer()
            if (initializer != null) {
                val webViewReference: WeakReference<WebView> =
                    WeakReference(WebView(context), WEB_VIEW_QUEUE)
                mWebView = webViewReference.get()
                mWebView = initializer.initWebView()
                mWebView?.webViewClient = initializer.initWebViewClient()
                mWebView?.webChromeClient = initializer.initWebViewChromeClient()
                mWebView?.addJavascriptInterface(FreakWebInterface.create(this), "Freak")
            } else {
                throw NullPointerException("initializer is null")
            }
        }
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