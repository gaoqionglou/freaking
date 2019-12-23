package com.kotlin.freaking.activity

import com.kotlin.freak_core.activity.ProxyActivity
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.launcher.LauncherDelegate

class MainActivity : ProxyActivity() {
    override fun setRootDelegate(): FreakDelegate {
        return LauncherDelegate()
    }


}
