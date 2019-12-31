package com.kotlin.freak_core.ui.recycler

class MultipleEntityBuilder() {
    init {
        FIELDS.clear()
    }

    companion object {
        val FIELDS: LinkedHashMap<Any?, Any?> = LinkedHashMap()
    }


    fun setItemType(itemType: Int): MultipleEntityBuilder {
        FIELDS[MutilpleFields.ITEM_TYPE] = itemType
        return this
    }

    fun setField(key: Any?, value: Any?): MultipleEntityBuilder {
        FIELDS[key] = value
        return this
    }

    fun setFields(map: LinkedHashMap<Any?, Any?>): MultipleEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}