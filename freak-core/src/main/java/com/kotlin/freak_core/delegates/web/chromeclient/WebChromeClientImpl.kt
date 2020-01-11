package com.kotlin.freak_core.delegates.web.chromeclient

import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class WebChromeClientImpl : WebChromeClient() {
    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        return super.onJsAlert(view, url, message, result)
    }
}