package com.kotlin.freak_ec.launcher

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.ui.launcher.ILauncherListener
import com.kotlin.freak_core.util.storage.FreakPreference
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.sign.SignInDelegate

class LauncherScrollDelegate : FreakDelegate(), OnItemClickListener {
    override fun onItemClick(position: Int) {
        if (position == INTEGERS.size - 1) {
            FreakPreference.setAppFlag(LanucherScrollStatus.HAS_FIRST_START_APP.name, true)
            start(SignInDelegate())
        }
    }

    private var mConvenientBanner: ConvenientBanner<Int>? = null
    private val INTEGERS = mutableListOf<Int>()
    var iLauncherListener: ILauncherListener? = null
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ILauncherListener) {
            iLauncherListener = activity
        }
    }
    fun initBanner() {
        INTEGERS.add(R.mipmap.launcher_01)
        INTEGERS.add(R.mipmap.launcher_02)
        INTEGERS.add(R.mipmap.launcher_03)
        INTEGERS.add(R.mipmap.launcher_04)
        INTEGERS.add(R.mipmap.launcher_05)
        INTEGERS.add(R.mipmap.launcher_06)

        mConvenientBanner?.let {
            it.setPages(LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(intArrayOf(R.drawable.dot_normal, R.drawable.dot_foucs))
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .isCanLoop = true
        }
    }

    override fun setLayout(): Any {
        mConvenientBanner = ConvenientBanner(context)
        return mConvenientBanner as ConvenientBanner<Int>
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        Log.i("Freak", "FreakPreference-" + FreakPreference.hashCode())
        initBanner()
    }

}



