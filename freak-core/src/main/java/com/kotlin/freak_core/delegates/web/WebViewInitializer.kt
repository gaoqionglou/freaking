package com.kotlin.freak_core.delegates.web

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView


class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(webView: WebView?): WebView? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        //cookie
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        }
        CookieManager.setAcceptFileSchemeCookies(true)

        //不能横向滚动
        webView?.isHorizontalScrollBarEnabled = false
        //不能纵向滚动
        webView?.isVerticalScrollBarEnabled = false
        //允许截图
        webView?.isDrawingCacheEnabled = true
        //屏蔽长按事件
        webView?.setOnLongClickListener { v -> true }
        //初始化WebSettings
        val settings = webView?.settings
        settings?.javaScriptEnabled = true
        val ua = settings?.userAgentString

        settings?.userAgentString = ua + "Freal"
        //隐藏缩放控件
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        //禁止缩放
        settings?.setSupportZoom(false)
        //文件权限
        settings?.allowFileAccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings?.allowFileAccessFromFileURLs = true
            settings?.allowUniversalAccessFromFileURLs = true
        }

        settings?.allowContentAccess = true
        //缓存相关
        settings?.setAppCacheEnabled(true)
        settings?.domStorageEnabled = true
        settings?.databaseEnabled = true
        settings?.cacheMode = WebSettings.LOAD_DEFAULT
        return webView


    }
}