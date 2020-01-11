package com.kotlin.freak_core.delegates.web.route

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.kotlin.freak_core.delegates.web.WebDelegate
import com.kotlin.freak_core.delegates.web.WebDelegateImpl

object Router {
    fun handleWebUrl(
        delegate: WebDelegate,
        url: String
    ): Boolean {


        if (url.contains("tel:")) {
            delegate.context?.let { callPhone(it, url) }
            return true
        }

        val topDelegate = delegate.mTopDelegate


        val webDelegate: WebDelegateImpl = WebDelegateImpl.create(url)

        topDelegate?.start(webDelegate)
        return true
    }


    private fun loadWebPage(webView: WebView?, url: String) {
        webView?.loadUrl(url)
    }

    fun loadPage(webView: WebView?, url: String) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url)
        } else {
            loadLocalPage(webView, url)
        }
    }

    fun loadPage(webDelegate: WebDelegate, url: String) {
        loadPage(webDelegate.mWebView, url)

    }

    private fun loadLocalPage(webView: WebView?, url: String) {
        loadWebPage(webView, "file:///android_asset/$url")
    }

    private fun callPhone(context: Context, uri: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse(uri)
        intent.data = data
//        context.startActivity(intent)
        ContextCompat.startActivity(context, intent, null)
    }
}