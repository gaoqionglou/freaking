package com.kotlin.freak_core.ui.recycler

abstract class DataConverter {
    val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    var mJsonData: String? = null
    abstract fun convert(): ArrayList<MultipleItemEntity>
    fun setJsonData(json: String?): DataConverter {
        this.mJsonData = json
        return this
    }

    fun getJsonData(): String? {
        if (this.mJsonData == null || this.mJsonData?.isEmpty() != false) {
            throw NullPointerException("data must not be null !")
        }
        return mJsonData
    }


}