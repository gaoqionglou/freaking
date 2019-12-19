package com.kotlin.freaking

import FontEcModule
import android.app.Application
import android.util.Log
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.kotlin.freak_core.Freak

class FreakApp : Application() {
    val TAG = "FreakApp";
    init {
        Log.i(TAG, " app init")
    }

    override fun onCreate() {
        super.onCreate()
        Freak.init(this)
            .withApiHost("https://127.0.0.1/")
            .withIcon(FontEcModule())
            .withIcon(FontAwesomeModule())
            .configure()
    }
}