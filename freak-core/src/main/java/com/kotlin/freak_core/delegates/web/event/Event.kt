package com.kotlin.freak_core.delegates.web.event

import android.content.Context
import android.webkit.WebView
import com.kotlin.freak_core.delegates.web.WebDelegate

abstract class Event(
    var mContext: Context? = null,
    var mAction: String? = null,
    var mDelegate: WebDelegate? = null,
    var mUrl: String? = null,
    var mWebView: WebView? = null
) : IEvent {


}