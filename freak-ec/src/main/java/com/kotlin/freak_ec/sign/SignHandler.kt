package com.kotlin.freak_ec.sign

import com.kotlin.freak_ec.database.DataBaseManager
import com.kotlin.freak_ec.database.UserProfileDao
import com.kotlin.freak_ec.database.bean.UserProfile


class SignHandler {
    companion object {

        fun signUp(userProfile: UserProfile) {
            DataBaseManager.mUserProfileDao?.insertOrReplace(userProfile)
        }


        fun signIn(email: String, password: String): Boolean {
            val datas = DataBaseManager.mUserProfileDao
                ?.queryBuilder()
                ?.where(
                    UserProfileDao.Properties.Email.eq(email),
                    UserProfileDao.Properties.Password.eq(password)
                )?.list()
            return datas != null && datas.size == 1
        }
    }
}