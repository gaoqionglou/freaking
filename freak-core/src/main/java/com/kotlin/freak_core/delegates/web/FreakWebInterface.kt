package com.kotlin.freak_core.delegates.web

import android.webkit.JavascriptInterface
import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.delegates.web.event.EventManager

class FreakWebInterface(val DELEGATE: WebDelegate) {
    companion object {
        fun create(delegate: WebDelegate): FreakWebInterface {
            return FreakWebInterface(delegate)
        }
    }


    @JavascriptInterface
    fun event(param: String): String {
        val action = JSON.parseObject(param).getString("action")
        val event = EventManager.createEvent(action)

        event.mAction = action
        event.mContext = DELEGATE.context
        event.mUrl = DELEGATE.mUrl
        event.mDelegate = DELEGATE
        event.mWebView = DELEGATE.mWebView
        return event.execute(param)

    }
}

