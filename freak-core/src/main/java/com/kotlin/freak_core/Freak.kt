package com.kotlin.freak_core

import android.app.Application
import android.content.Context
import java.util.*

class Freak {

    companion object {
        fun init(context: Context): Configurator {
            getConfigurations()[ConfigType.APPLIICATION_CONTEXT.name] = context.applicationContext
            return Configurator
        }

        fun getConfigurations(): HashMap<String, Any> {
            return Configurator.FREAK_CONFIGS
        }

          fun getApplication(): Context {
            return getConfigurations()[ConfigType.APPLIICATION_CONTEXT.name] as Context

        }
    }

}