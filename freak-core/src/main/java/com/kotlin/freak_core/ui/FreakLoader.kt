package com.kotlin.freak_core.ui

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.kotlin.freak_core.R
import com.kotlin.freak_core.util.DimenUtil

class FreakLoader {


    companion object {
        private val LOADER_SIZE_SCALE = 8
        private val LOADER_OFFSET_SCALE = 10
        val DEFAULT_LOADER_STYLE = LoaderStyle.BallGridBeatIndicator
        val DEFAULT_LOADER = DEFAULT_LOADER_STYLE.name
        val LOADERS = mutableListOf<AppCompatDialog?>()

        fun showLoading(context: Context?, type: Enum<LoaderStyle>) {
            showLoading(context, type.name)
        }

        fun showLoading(context: Context?, type: String) {
            val dialog = AppCompatDialog(context, R.style.dialog)

            val avLoadingIndicatorView = LoaderCreator.create(type, context)
            dialog.setContentView(avLoadingIndicatorView)
            val deviceWidth = DimenUtil.getScreenWidth()
            val deviceHeight = DimenUtil.getScreenHeight()
            val dialogWindow = dialog.window
            if (dialogWindow != null) {
                val lp: WindowManager.LayoutParams = dialogWindow.attributes
                lp.width = deviceWidth / LOADER_SIZE_SCALE
                lp.height = deviceHeight / LOADER_SIZE_SCALE
                lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE
                lp.gravity = Gravity.CENTER
            }
            LOADERS.add(dialog)
            dialog.show()

        }

        fun showLoading(context: Context?) {
            showLoading(context, DEFAULT_LOADER)
        }

        fun stopLoading() {
            LOADERS.forEach {
                it?.let {
                    if (it.isShowing) {
                        it.cancel()
                    }
                }
            }
        }
    }
}