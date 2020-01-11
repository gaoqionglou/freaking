package com.kotlin.freak_core.delegates.web.event

class UndefineEvent : Event() {
    override fun execute(params: String): String {

        return "undefine"
    }

}