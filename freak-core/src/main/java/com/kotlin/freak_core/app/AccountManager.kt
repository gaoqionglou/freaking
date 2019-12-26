package com.kotlin.freak_core.app

import com.kotlin.freak_core.util.storage.FreakPreference

class AccountManager {
    companion object {
        fun setLoginStatus(isLogin: Boolean) {
            FreakPreference.setAppFlag(SignTag.SIGN_TAG.name, true)
        }

        fun isSignIn(): Boolean {
            return FreakPreference.getAppFlag(SignTag.SIGN_TAG.name)
        }

        fun checkAccount(iUserChecker: IUserChecker?) {
            if (isSignIn()) {
                iUserChecker?.onSignIn()
            } else {
                iUserChecker?.onNotSignIn()
            }
        }

    }


}


enum class SignTag {
    SIGN_TAG;
}