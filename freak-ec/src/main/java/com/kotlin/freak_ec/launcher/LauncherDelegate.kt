package com.kotlin.freak_ec.launcher

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.OnClick
import com.kotlin.freak_core.app.AccountManager
import com.kotlin.freak_core.app.IUserChecker
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.ui.launcher.ILauncherListener
import com.kotlin.freak_core.ui.launcher.OnLaucherFinishTag
import com.kotlin.freak_core.util.storage.FreakPreference
import com.kotlin.freak_core.util.timer.BaseTimerTask
import com.kotlin.freak_core.util.timer.ITimerListener
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class LauncherDelegate : FreakDelegate(), ITimerListener {


    var timer: Timer? = null
    var timerTask: BaseTimerTask? = null
    var mCount = 5

    @BindView(R2.id.delegate_launcher_text)
    @JvmField
    var mTvTimer: AppCompatTextView? = null


    @OnClick(R2.id.delegate_launcher_text)
    fun onClickTimer() {
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null
        checkIfShowScroll()
    }


    var iLauncherListener: ILauncherListener? = null
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ILauncherListener) {
            iLauncherListener = activity
        }
    }


    private fun initTimer() {

        timer = Timer()
        timerTask = BaseTimerTask(this)
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
                if (mCount <= 0) {
                    timer?.cancel()
                    timer = null
                    timerTask?.cancel()
                    timerTask = null
                    checkIfShowScroll()
                }

            }

        })

    }


    fun checkIfShowScroll() {
        if (!FreakPreference.getAppFlag(LanucherScrollStatus.HAS_FIRST_START_APP.name)) {
            start(LauncherScrollDelegate(), SupportFragment.SINGLETASK)
        } else {
            //是否登录
            AccountManager.checkAccount(object : IUserChecker {
                override fun onSignIn() {
                    iLauncherListener?.onLauncherFinish(OnLaucherFinishTag.SIGNED)
                }

                override fun onNotSignIn() {
                    iLauncherListener?.onLauncherFinish(OnLaucherFinishTag.NOTSIGNED)
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null

    }
}