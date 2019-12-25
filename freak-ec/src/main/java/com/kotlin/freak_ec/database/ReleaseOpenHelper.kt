package com.kotlin.freak_ec.database

import android.content.Context
import org.greenrobot.greendao.database.Database

class ReleaseOpenHelper(context: Context?, name: String?) : DaoMaster.OpenHelper(context, name) {


    override fun onCreate(db: Database?) {
        super.onCreate(db)
    }
}