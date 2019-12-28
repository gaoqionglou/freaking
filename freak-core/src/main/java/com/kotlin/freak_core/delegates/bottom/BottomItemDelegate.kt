package com.kotlin.freak_core.delegates.bottom

import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.kotlin.freak_core.R
import com.kotlin.freak_core.delegates.FreakDelegate


abstract class BottomItemDelegate : FreakDelegate(), View.OnKeyListener {
    private var mExitTime = 0
    private val EXIT_TIME = 2000


    override fun onResume() {
        super.onResume()
        val rootView = view
        if(rootView!=null){
            rootView.isFocusableInTouchMode = true
            rootView.requestFocus()
            rootView.setOnKeyListener(this)
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
                Toast.makeText(context, "双击退出" + getString(R.string.app_name), Toast.LENGTH_LONG)
                    .show()
            } else {
                _mActivity.finish()
                if (mExitTime != 0) {
                    mExitTime = 0
                }
            }
            //消化掉这个事件
            return true
        }
        return false
    }
}