package com.kotlin.freak_ec.database

import android.content.Context

object DataBaseManager {
    var mDaoSession: DaoSession? = null
    var mUserProfileDao: UserProfileDao? = null


    fun init(context: Context): DataBaseManager {
        initDao(context)
        return this
    }

    @Suppress("UsePropertyAccessSyntax")
    fun initDao(context: Context) {
        val helper = ReleaseOpenHelper(context, "freak_ec.db")
        val db = helper.writableDb
        mDaoSession = DaoMaster(db).newSession()
        mUserProfileDao = mDaoSession?.userProfileDao
    }

}