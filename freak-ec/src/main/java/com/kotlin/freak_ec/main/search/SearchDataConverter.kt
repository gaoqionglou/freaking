package com.kotlin.freak_ec.main.search

import com.alibaba.fastjson.JSONArray
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_core.util.storage.FreakPreference


class SearchDataConverter : DataConverter() {
    companion object {
        const val TAG_SEARCH_HISTORY = "search_history"
    }

    override fun convert(): ArrayList<MultipleItemEntity> {
        val jsonStr = FreakPreference.getCustomAppProfile(TAG_SEARCH_HISTORY)
        if (!jsonStr.isNullOrEmpty()) {
            val array = JSONArray.parseArray(jsonStr)
            val size = array.size
            for (i in 0 until size) {
                val historyItemText = array.getString(i)
                val entity = MultipleItemEntity.builder()
                    .setItemType(SearchItemType.ITEM_SEARCH)
                    .setField(MutilpleFields.TEXT, historyItemText)
                    .build()
                ENTITIES.add(entity)
            }
        }
        return ENTITIES
    }

}