package com.kotlin.freaking.app

import android.widget.Toast
import com.kotlin.freak_core.activity.ProxyActivity
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.ui.launcher.ILauncherListener
import com.kotlin.freak_core.ui.launcher.OnLaucherFinishTag
import com.kotlin.freak_ec.launcher.LauncherDelegate
import com.kotlin.freak_ec.main.EcBottomDelegate
import com.kotlin.freak_ec.sign.ISignLIstener
import com.kotlin.freak_ec.sign.SignUpDelegate

class MainActivity : ProxyActivity(), ISignLIstener, ILauncherListener {
    override fun onLauncherFinish(onLaucherFinishTag: OnLaucherFinishTag) {
        when (onLaucherFinishTag) {
            OnLaucherFinishTag.SIGNED -> {
//                Toast.makeText(
//                    this,
//                    "启动结束，用户登录了",
//                    Toast.LENGTH_LONG
//                ).show()
                startWithPop(EcBottomDelegate())
            }
            OnLaucherFinishTag.NOTSIGNED -> {
//                Toast.makeText(this, "启动结束，用户未登录", Toast.LENGTH_LONG).show()
                startWithPop(SignUpDelegate())
            }
        }
    }

    override fun onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show()
    }

    override fun onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
    }

    override fun setRootDelegate(): FreakDelegate {
        return LauncherDelegate()
    }


}
