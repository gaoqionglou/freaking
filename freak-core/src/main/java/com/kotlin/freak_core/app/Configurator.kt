package com.kotlin.freak_core.app

import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import okhttp3.Interceptor
import java.util.*


object Configurator {
    init {
        print("Configurator init")
    }

    val FREAK_CONFIGS: HashMap<Any, Any> = HashMap()
    val ICONS = mutableListOf<IconFontDescriptor>()
    val INTERCEPTORS = mutableListOf<Interceptor>()


    fun configure() {
        FREAK_CONFIGS[ConfigKey.CONFIG_READY.name] = true
        initIcons()
    }

    fun withApiHost(host: String): Configurator {
        FREAK_CONFIGS[ConfigKey.API_HOST.name] = host
        return this
    }

    fun withIcon(descriptor: IconFontDescriptor): Configurator {
        ICONS.add(descriptor)
        return this
    }


    fun withInterceptor(interceptor: Interceptor): Configurator {
        INTERCEPTORS.add(interceptor)
        FREAK_CONFIGS[ConfigKey.INTERCEPTORS] =
            INTERCEPTORS
        return this

    }

    fun withInterceptors(interceptors: ArrayList<Interceptor>): Configurator {
        INTERCEPTORS.addAll(interceptors)
        FREAK_CONFIGS[ConfigKey.INTERCEPTORS] =
            INTERCEPTORS
        return this

    }

    private fun initIcons() {
        if (ICONS.size > 0) {
            val initializer: Iconify.IconifyInitializer = Iconify.with(ICONS[0])
            ICONS.forEach { initializer.with(it) }
        }
    }

    fun checkConfiguration() {
        val isReady = FREAK_CONFIGS[ConfigKey.CONFIG_READY.name] as Boolean
        if (isReady) {
            throw RuntimeException("Configuration not ready, call configure .")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getConfiguratioin(key: Enum<ConfigKey>): T {
        checkConfiguration()
        return FREAK_CONFIGS[key.name] as T
    }
}

