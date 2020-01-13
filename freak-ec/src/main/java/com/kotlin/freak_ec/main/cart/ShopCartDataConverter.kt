package com.kotlin.freak_ec.main.cart

import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields

class ShopCartDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()

        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val thumb = data.getString("thumb")
            val desc = data.getString("desc")
            val title = data.getString("title")
            val id = data.getInteger("id")
            val count = data.getInteger("count")
            val price = data.getDouble("price")
            val entity = MultipleItemEntity.builder()
                .setField(MutilpleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                .setField(MutilpleFields.ID, id)
                .setField(MutilpleFields.IMAGE_URL, thumb)
                .setField(ShopCartItemFields.TITLE, title)
                .setField(ShopCartItemFields.DESC, desc)
                .setField(ShopCartItemFields.COUNT, count)
                .setField(ShopCartItemFields.PRICE, price)
                .setField(ShopCartItemFields.IS_SELECTED, false)
                .setField(ShopCartItemFields.POSITION, i)
                .build()
            dataList.add(entity)
        }



        return dataList
    }

}