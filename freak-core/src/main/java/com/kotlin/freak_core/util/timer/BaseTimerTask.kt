package com.kotlin.freak_core.util.timer

import java.util.*

class BaseTimerTask(private val iTimerListener: ITimerListener?) : TimerTask() {

    override fun run() {
        iTimerListener?.onTimer()
    }

}

public interface ITimerListener {
    fun onTimer()
}