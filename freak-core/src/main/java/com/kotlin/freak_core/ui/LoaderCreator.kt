package com.kotlin.freak_core.ui

import android.content.Context
import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import java.util.*

class LoaderCreator {
    companion object {
        private val LOADING_MAP: WeakHashMap<String, Indicator> = WeakHashMap()

        fun create(type: String, context: Context?): AVLoadingIndicatorView {
            val avLoadingIndicatorView = AVLoadingIndicatorView(context)
            if (LOADING_MAP[type] == null) {
                val indicator = getIndicator(type)
                LOADING_MAP[type] = indicator

            }
            avLoadingIndicatorView.indicator = LOADING_MAP[type]
            return avLoadingIndicatorView
        }


        private fun getIndicator(name: String?): Indicator? {
            if (name == null || name.isEmpty()) {
                return null
            }

            val drawableClassName: StringBuilder = StringBuilder()
            if (!name.contains(".")) {
                @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                val defaultPackageName = AVLoadingIndicatorView::class.java.`package`.name
                drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".")
            }
            drawableClassName.append(name)
            val drawableClass: Class<*> = Class.forName(drawableClassName.toString())
            return drawableClass.newInstance() as Indicator
        }
    }
}