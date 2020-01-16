package com.kotlin.freak_core.util.callback


import java.util.*

class CallbackManager {

    private object Holder {
        val INSTANCE = CallbackManager()
    }

    fun addCallback(tag: Any, callback: IGlobalCallback<*>): CallbackManager {
        CALLBACKS[tag] = callback
        return this
    }

    fun getCallback(tag: Any): IGlobalCallback<*>? {
        return CALLBACKS[tag]
    }

    companion object {

        private val CALLBACKS = WeakHashMap<Any, IGlobalCallback<*>>()

        val instance: CallbackManager
            get() = Holder.INSTANCE
    }
}