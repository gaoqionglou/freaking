package com.kotlin.freak_ec.sign

import com.kotlin.freak_ec.database.DataBaseManager
import com.kotlin.freak_ec.database.bean.UserProfile


object SignUpHandler {
    fun signUp(userProfile: UserProfile) {
        DataBaseManager.mUserProfileDao?.insertOrReplace(userProfile)
    }
}