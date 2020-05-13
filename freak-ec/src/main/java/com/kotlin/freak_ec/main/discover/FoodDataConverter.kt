package com.kotlin.freak_ec.main.discover

import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields

class FoodDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()

        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val id = data.getString("id")
            val imageUrl = data.getString("imageUrl")
            val title = data.getString("title")
            val address = data.getString("address")
            val tel = data.getString("tel")
            val commentCount = data.getString("commentCount")

            val entity = MultipleItemEntity.builder()
                .setField(MutilpleFields.ITEM_TYPE, FoodItemType.FOOD_ITEM)
                .setField(MutilpleFields.ID, id)
                .setField(FoodItemFields.IMAGE_URL, imageUrl)
                .setField(FoodItemFields.TITLE, title)
                .setField(FoodItemFields.ADDRESS, address)
                .setField(FoodItemFields.TEL, tel)
                .setField(FoodItemFields.COMMENT_COUNT, commentCount)
                .build()
            dataList.add(entity)
        }



        return dataList
    }
}