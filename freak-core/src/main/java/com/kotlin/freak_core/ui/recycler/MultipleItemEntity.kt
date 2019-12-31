package com.kotlin.freak_core.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference

class MultipleItemEntity() : MultiItemEntity {

    companion object {
        fun builder(): MultipleEntityBuilder {
            return MultipleEntityBuilder()
        }
    }

    private val ITEM_QUENE: ReferenceQueue<LinkedHashMap<Any?, Any?>> = ReferenceQueue()
    private val MUTIPLE_FIELDS: LinkedHashMap<Any?, Any?> = LinkedHashMap()
    private val FIELDS_REFERENCES: SoftReference<LinkedHashMap<Any?, Any?>> =
        SoftReference(MUTIPLE_FIELDS, ITEM_QUENE)


    constructor(fields: LinkedHashMap<Any?, Any?>) : this() {
        FIELDS_REFERENCES.get()?.putAll(fields)
    }


    override val itemType: Int
        get() = FIELDS_REFERENCES.get()?.get(MutilpleFields.ITEM_TYPE) as Int


    @Suppress("UNCHECKED_CAST")
    fun <T> getField(key: Any): T {
        return FIELDS_REFERENCES.get()?.get(key) as T
    }

    fun setField(key: Any, value: Any): MultiItemEntity {
        FIELDS_REFERENCES.get()?.let { it[key] = value }
        return this
    }

    fun getFields(): LinkedHashMap<Any?, Any?>? {
        return FIELDS_REFERENCES.get()
    }
}




