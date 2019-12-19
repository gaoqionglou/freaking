package com.kotlin.freak_core

import java.util.*


object Configurator {
    init {
        print("Configurator init")
    }

    val FREAK_CONFIGS: WeakHashMap<String, Any> = WeakHashMap()


    fun configure() {
        FREAK_CONFIGS[ConfigType.CONFIG_READY.name] = true
    }

    fun withApiHost(host: String): Configurator {
        FREAK_CONFIGS[ConfigType.API_HOST.name] = host
        return this
    }

    fun checkConfiguration() {
        val isReady = FREAK_CONFIGS[ConfigType.CONFIG_READY.name] as Boolean
        if (isReady) {
            throw RuntimeException("Configuration not ready, call configure .")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getConfiguratioin(key: Enum<ConfigType>): T {
        checkConfiguration()
        return FREAK_CONFIGS[key.name] as T
    }
}


