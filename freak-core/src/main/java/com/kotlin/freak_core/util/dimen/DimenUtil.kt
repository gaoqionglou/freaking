package com.kotlin.freak_core.util.dimen

import com.kotlin.freak_core.app.Freak

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