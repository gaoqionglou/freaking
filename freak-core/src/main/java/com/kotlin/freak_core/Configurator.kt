package com.kotlin.freak_core

import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import java.util.*
import kotlin.collections.ArrayList


object Configurator {
    init {
        print("Configurator init")
    }

    val FREAK_CONFIGS: WeakHashMap<String, Any> = WeakHashMap()
    val ICONS = ArrayList<IconFontDescriptor>()

    fun configure() {
        FREAK_CONFIGS[ConfigType.CONFIG_READY.name] = true
        initIcons()
    }

    fun withApiHost(host: String): Configurator {
        FREAK_CONFIGS[ConfigType.API_HOST.name] = host
        return this
    }

    fun withIcon(descriptor: IconFontDescriptor): Configurator {
        ICONS.add(descriptor)
        return this
    }

    private fun initIcons() {
        if (ICONS.size > 0) {
            val initializer: Iconify.IconifyInitializer = Iconify.with(ICONS[0])
            ICONS.forEach { initializer.with(it) }
        }
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


