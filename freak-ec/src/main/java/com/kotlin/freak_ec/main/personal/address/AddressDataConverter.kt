package com.kotlin.freak_ec.main.personal.address

import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields

class AddressDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val array = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = array.size
        for (i in 0 until size) {
            val a = array.getJSONObject(i)
            val default: Boolean = a.getBoolean("default")
            val name: String = a.getString("name")
            val id: Int = a.getInteger("id")
            val phone: String = a.getString("phone")
            val address: String = a.getString("address")
            val entity = MultipleItemEntity.builder().setItemType(AddressItemType.ITEM_ADDRESS)
                .setField(MutilpleFields.ID, id)
                .setField(MutilpleFields.NAME, name)
                .setField(MutilpleFields.TAG, default)
                .setField(AddressItemFields.PHONE, phone)
                .setField(AddressItemFields.ADDRESS, address)
                .build()
            ENTITIES.add(entity)
        }

        return ENTITIES

    }

}