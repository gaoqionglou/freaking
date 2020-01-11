package com.kotlin.freak_core.delegates.web.event

interface IEvent {
    fun execute(params: String): String
}