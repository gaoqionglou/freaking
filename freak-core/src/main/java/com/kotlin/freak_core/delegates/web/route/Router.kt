package com.kotlin.freak_core.delegates.web.route

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.kotlin.freak_core.delegates.FreakDelegate
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

        val parentDelegate: FreakDelegate? = delegate.getParentDelegate()
        val webDelegate: WebDelegateImpl = WebDelegateImpl.create(url)
        if (parentDelegate == null) {
            delegate.start(webDelegate)
        } else {
            parentDelegate.start(webDelegate)
        }

        return true
    }


    private fun loadWebPage(webView: WebView?, url: String) {
        webView?.loadUrl(url)
    }

    private fun loadLocalPage(webView: WebView?, url: String) {
        loadWebPage(webView, "file:///android_assets/$url")
    }

    private fun callPhone(context: Context, uri: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse(uri)
        intent.data = data
//        context.startActivity(intent)
        ContextCompat.startActivity(context, intent, null)
    }
}