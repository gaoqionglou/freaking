package com.kotlin.freak_ec.main.personal.order

import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields

class OrderListDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val array = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = array.size
        for (i in 0 until size) {
            val a = array.getJSONObject(i)
            val thumb: String = a.getString("thumb")
            val title: String = a.getString("title")
            val id: Int = a.getInteger("id")
            val price: Double = a.getDouble("price")
            val time: String = a.getString("time")
            val entity = MultipleItemEntity.builder().setItemType(OrderListItemType.ITEM_ORDER_LIST)
                .setField(MutilpleFields.ID, id)
                .setField(MutilpleFields.IMAGE_URL, thumb)
                .setField(MutilpleFields.TITLE, title)
                .setField(OrderItemFields.PRICE, price)
                .setField(OrderItemFields.TIME, time)
                .build()
            ENTITIES.add(entity)
        }


        return ENTITIES
    }

}