package com.kotlin.freak_ec.main.index

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.ItemType
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields


class IndexDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray: JSONArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        for (index in 0 until size) {
            val data: JSONObject = dataArray.getJSONObject(index)
            val imageUrl = data.getString("imageUrl")
            val text = data.getString("text")
            val spanSize = data.getInteger("spanSize")
            val id = data.getInteger("goodsId")
            val banners = data.getJSONArray("banners")
            val bannerImages = ArrayList<String>()
            var type = 0
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE
            } else if (imageUrl != null && text != null) {
                type = ItemType.TEXT_IMAGE
            } else if (banners != null) {
                type = ItemType.BANNER
                val bannerSize = banners.size
                for (j in 0 until bannerSize) {
                    val banner = banners.getString(j)
                    bannerImages.add(banner)
                }
            }
            val entity: MultipleItemEntity = MultipleItemEntity.builder()
                .setField(MutilpleFields.ITEM_TYPE, type)
                .setField(MutilpleFields.SPAN_SIZE, spanSize)
                .setField(MutilpleFields.ID, id)
                .setField(MutilpleFields.TEXT, text)
                .setField(MutilpleFields.IMAGE_URL, imageUrl)
                .setField(MutilpleFields.BANNERS, bannerImages)
                .build()
            ENTITIES.add(entity)
        }

        return ENTITIES
    }

}