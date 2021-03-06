package com.kotlin.freak_core.util.storage

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.kotlin.freak_core.app.Freak

/**
 * activity.getPreference(int mode )生成activity名.xml. 用于activity内部存储
 * PreferenceManager.getDefaultSharedPreferences生成包名_preference.xml
 * Context.getSharePreference(name, mode)生成name.xml
 */
object FreakPreference {
    private val PREFERENCES = PreferenceManager.getDefaultSharedPreferences(Freak.getApplication())
    fun getAppPreference(): SharedPreferences {
        return PREFERENCES
    }

    fun setAppFlag(key: String, flag: Boolean) {
        getAppPreference().edit().putBoolean(key, flag).apply()
    }

    fun getAppFlag(key: String): Boolean {
        return getAppPreference().getBoolean(key, false)
    }

    fun addCustomAppProfile(key: String, value: String) {
        getAppPreference()
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getCustomAppProfile(key: String): String? {
        return getAppPreference().getString(key, "")
    }

}


