package com.kotlin.freaking.activity

import com.kotlin.freaking.MainDelegate
import com.kotlin.freaking.delegates.FreakDelegate

class MainActivity : ProxyActivity() {
    override fun setRootDelegate(): FreakDelegate {
        return MainDelegate()
    }


}
