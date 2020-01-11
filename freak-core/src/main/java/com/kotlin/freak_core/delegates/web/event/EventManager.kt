package com.kotlin.freak_core.delegates.web.event

object EventManager {
    @JvmStatic
    val EVENTS = HashMap<String, Event>()


    fun addEvent(name: String, event: Event): EventManager {
        EVENTS[name] = event
        return this
    }

    fun createEvent(action: String): Event {
        val event = EVENTS[action]
        if (event == null) {
            return UndefineEvent()
        }
        return event
    }
}