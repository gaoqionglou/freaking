package com.kotlin.freaking

import android.app.Application
import android.util.Log

class FreakApp : Application() {
    val TAG = "FreakApp";
    init {
        Log.i(TAG," app init");
    }

    override fun onCreate() {
        super.onCreate()
    }
}