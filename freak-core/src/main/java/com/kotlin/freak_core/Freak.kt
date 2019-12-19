package com.kotlin.freak_core

import android.content.Context
import java.util.*

class Freak {

    companion object {
        fun init(context: Context): Configurator {
            getConfigurations()[ConfigType.APPLIICATION_CONTEXT.name] = context.applicationContext
            return Configurator
        }

        private fun getConfigurations(): WeakHashMap<String, Any> {
            return Configurator.FREAK_CONFIGS
        }
    }

}