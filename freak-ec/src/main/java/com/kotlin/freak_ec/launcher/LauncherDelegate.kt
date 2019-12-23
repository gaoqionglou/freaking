package com.kotlin.freak_ec.launcher

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.OnClick
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.timer.BaseTimerTask
import com.kotlin.freak_core.util.timer.ITimerListener
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import java.util.*

class LauncherDelegate : FreakDelegate(), ITimerListener {


    var timer: Timer? = null
    var mCount = 5

    @BindView(R2.id.delegate_launcher_text)
    @JvmField
    var mTvTimer: AppCompatTextView? = null


    @OnClick(R2.id.delegate_launcher_text)
    fun onClickTimer() {

    }


    private fun initTimer() {
        timer = Timer()
        val timerTask = BaseTimerTask(this)
        timer?.schedule(timerTask, 0, 1000)
    }

    override fun setLayout(): Any {
        return R.layout.delegate_launcher
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initTimer()
    }

    override fun onTimer() {
/*        getProxyActivity().runOnUiThread {
            Runnable {
                mTvTimer?.text = "跳过\n$mCount 秒"
                mCount--
                if (mCount < 0) {
                    timer?.cancel()
                    timer = null
                }
            }
        }*/
        getProxyActivity().runOnUiThread(object : Runnable {
            override fun run() {
                mTvTimer?.text = "跳过\n$mCount 秒"
                mCount--
                if (mCount < 0) {
                    timer?.cancel()
                    timer = null
                }
            }

        })

    }

}