package com.kotlin.freak_core.delegates.web

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kotlin.freak_core.delegates.web.chromeclient.WebChromeClientImpl
import com.kotlin.freak_core.delegates.web.client.WebViewClientImpl
import com.kotlin.freak_core.delegates.web.route.RouteKeys
import com.kotlin.freak_core.delegates.web.route.Router

class WebDelegateImpl : WebDelegate() {


    companion object {
        fun create(url: String): WebDelegateImpl {
            val args = Bundle()
            args.putString(RouteKeys.URL.name, url)
            val delegate = WebDelegateImpl()
            delegate.arguments = args
            return delegate
        }
    }


    override fun setInitializer(): IWebViewInitializer? {
        return this
    }

    override fun setLayout(): Any {
        return mWebView as WebView
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        if (mUrl != null) {
            //用原生方法模拟web跳转并加载页面
            Router.loadPage(this, mUrl ?: "")
        }
    }

    override fun initWebView(webView: WebView?): WebView {
        return WebViewInitializer().createWebView(webView) as WebView
    }

    override fun initWebViewClient(): WebViewClient? {
        val webViewClient = WebViewClientImpl(this)
        return webViewClient
    }

    override fun initWebViewChromeClient(): WebChromeClient? {
        return WebChromeClientImpl()
    }
}