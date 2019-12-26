package com.kotlin.freak_core.ui.launcher

interface ILauncherListener {
    fun onLauncherFinish(onLaucherFinishTag: OnLaucherFinishTag)
}

enum class OnLaucherFinishTag {
    SIGNED, NOTSIGNED
}