package com.kotlin.freak_core.delegates.web.client

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.delegates.web.IPageLoadListener
import com.kotlin.freak_core.delegates.web.WebDelegate
import com.kotlin.freak_core.delegates.web.route.Router
import com.kotlin.freak_core.ui.loader.FreakLoader

class WebViewClientImpl(val DELEGATE: WebDelegate) : WebViewClient() {

    private var pageLoadListener: IPageLoadListener? = null

    fun setPageLoadListener(pageLoadListener: IPageLoadListener) {
        this.pageLoadListener = pageLoadListener
    }


    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        Toast.makeText(Freak.getApplication(), "$url", Toast.LENGTH_SHORT).show()
        return Router.handleWebUrl(DELEGATE, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(
                "gql",
                error?.description.toString() + "\n" + error?.errorCode + "\n" + error.toString()
            )
        }
        super.onReceivedError(view, request, error)
    }


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        pageLoadListener?.onLoadStart()
        FreakLoader.showLoading(view?.context)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        pageLoadListener?.onLoadEnd()
        Freak.getHandler().postDelayed(object : Runnable {
            override fun run() {
                FreakLoader.stopLoading()
            }

        }, 2000)
    }

}