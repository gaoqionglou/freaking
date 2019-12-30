package com.kotlin.freak_core.ui.recycler

class MutipleEntityBuilder() {
    init {
        FIELDS.clear()
    }

    companion object {
        val FIELDS: LinkedHashMap<Any?, Any?> = LinkedHashMap()
    }


    fun setItemType(itemType: Int): MutipleEntityBuilder {
        FIELDS[MutilpleFields.ITEM_TYPE] = itemType
        return this
    }

    fun setField(key: Any?, value: Any?): MutipleEntityBuilder {
        FIELDS[key] = value
        return this
    }

    fun setFields(map: LinkedHashMap<Any?, Any?>): MutipleEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}