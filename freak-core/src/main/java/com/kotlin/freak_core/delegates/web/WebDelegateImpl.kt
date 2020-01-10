package com.kotlin.freak_core.delegates.web

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.kotlin.freak_core.delegates.web.route.RouteKeys

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLayout(): Any {
        return mWebView as WebView
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        if (mUrl != null) {
            //用原生方法模拟web跳转
        }
    }

}