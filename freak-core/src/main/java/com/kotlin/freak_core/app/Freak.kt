package com.kotlin.freak_core.app

import android.content.Context
import android.os.Handler
import android.os.Message

import java.util.*


class Freak {


    companion object {
        private val handler: FreakHandler = FreakHandler()

        fun getHandler(): FreakHandler {
            return handler
        }


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


    public class FreakHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

}