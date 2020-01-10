package com.kotlin.freak_core.delegates

abstract class FreakDelegate : PermissionCheckerDelegate() {

    @Suppress("UNCHECKED_CAST")
    open fun <T : FreakDelegate> getParentDelegate(): T? {
        return parentFragment as T?
    }


}