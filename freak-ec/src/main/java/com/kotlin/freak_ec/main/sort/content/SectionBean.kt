package com.kotlin.freak_ec.main.sort.content

import com.chad.library.adapter.base.entity.SectionEntity

class SectionBean(entity: SectionContentItemEntity, override val isHeader: Boolean) :
    SectionEntity {
    private var mIsMore = false
    private var mId = -1

}


class SectionContentItemEntity(
    private var goodsId: Int = 0,
    private var goodsName: String? = null,
    private var goodsThumb: String? = null
)