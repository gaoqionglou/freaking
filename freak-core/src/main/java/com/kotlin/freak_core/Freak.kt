package com.kotlin.freak_core

import android.content.Context
import java.util.*

class Freak {

    companion object {
        fun init(context: Context): Configurator {
            getConfigurations()[ConfigKey.APPLIICATION_CONTEXT.name] = context.applicationContext
            return Configurator
        }

        fun getConfigurations(): HashMap<Any, Any> {
            return Configurator.FREAK_CONFIGS
        }

        fun getConfiguration(any: Any): Any? {
            return Configurator.FREAK_CONFIGS[any]
        }

          fun getApplication(): Context {
              return getConfigurations()[ConfigKey.APPLIICATION_CONTEXT.name] as Context

        }
    }

}