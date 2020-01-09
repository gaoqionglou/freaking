package com.kotlin.freak_ec.main.sort.list

import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.ItemType
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields

class VerticalListDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        val dataArray = JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val id = data.getInteger("id")
            val name = data.getString("name")
            val entity = MultipleItemEntity.builder()
                .setField(MutilpleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                .setField(MutilpleFields.ID, id)
                .setField(MutilpleFields.TEXT, name)
                .setField(MutilpleFields.TAG, false)
                .build()
            dataList.add(entity)
        }
        //设置第一个选中
        dataList[0].setField(MutilpleFields.TAG, true)
        return dataList
    }

}