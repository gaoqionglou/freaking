package com.kotlin.freak_core.delegates.web.client

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kotlin.freak_core.delegates.web.WebDelegate
import com.kotlin.freak_core.delegates.web.route.Router

class WebViewClientImpl(val DELEGATE: WebDelegate) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Log.i("WebViewClientImpl", "shouldOverrideUrlLoading")
        return Router.handleWebUrl(DELEGATE, url)
    }
}