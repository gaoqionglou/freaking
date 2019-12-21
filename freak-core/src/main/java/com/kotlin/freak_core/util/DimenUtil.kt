package com.kotlin.freak_core.util

import com.kotlin.freak_core.Freak

class DimenUtil {
    companion object {
        fun getScreenWidth(): Int {
            val resource = Freak.getApplication().resources
            val displayMetrics = resource.displayMetrics
            return displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            val resource = Freak.getApplication().resources
            val displayMetrics = resource.displayMetrics
            return displayMetrics.heightPixels
        }
    }

}