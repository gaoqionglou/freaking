package com.kotlin.freak_ec.main.sort.content

import com.alibaba.fastjson.JSON

class SectionDataConverter {

    fun convert(json: String): ArrayList<SectionBean> {
        val dataList = ArrayList<SectionBean>()
        val jsonArray = JSON.parseObject(json).getJSONArray("data")
        val size = jsonArray.size
        for (i in 0 until size) {
            val data = jsonArray.getJSONObject(i)
            val id = data.getInteger("id")
            val title = data.getString("section")

            //添加title
            val sectionBean = SectionBean(null, title, true)
            sectionBean.id = id
            sectionBean.isMore = true
            dataList.add(sectionBean)

            val goods = data.getJSONArray("goods")
            val goodsize = goods.size
            for (j in 0 until goodsize) {
                val contentItem = goods.getJSONObject(j)
                val goodsId = contentItem.getInteger("goods_id")
                val goodsName = contentItem.getString("goods_name")
                val goodsThumb = contentItem.getString("goods_thumb")

                val sectionContentItemEntity =
                    SectionContentItemEntity(goodsId, goodsName, goodsThumb)
                //添加内容
                dataList.add(SectionBean(sectionContentItemEntity, null, false))
            }
        }

        return dataList
    }

}