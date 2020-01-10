package com.kotlin.freak_core.delegates.web

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

interface IWebViewInitializer {
    fun initWebView(): WebView
    fun initWebViewClient(): WebViewClient
    fun initWebViewChromeClient(): WebChromeClient

}