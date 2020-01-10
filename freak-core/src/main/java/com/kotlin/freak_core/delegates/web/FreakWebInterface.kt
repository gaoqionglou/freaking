package com.kotlin.freak_core.delegates.web

import com.alibaba.fastjson.JSON

class FreakWebInterface(val DELEGATE: WebDelegate) {
    companion object {
        fun create(delegate: WebDelegate): FreakWebInterface {
            return FreakWebInterface(delegate)
        }
    }


    fun event(param: String): Any? {
        val action = JSON.parseObject(param).getString("action")
        return null
    }
}