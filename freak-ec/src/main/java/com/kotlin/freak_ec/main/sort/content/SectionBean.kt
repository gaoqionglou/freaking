package com.kotlin.freak_ec.main.sort.content

import com.chad.library.adapter.base.entity.SectionEntity

class SectionBean(
    val entity: SectionContentItemEntity?,
    val headerTitle: String?,
    override val isHeader: Boolean
) :
    SectionEntity {
    var isMore = false
    var id = -1

}


class SectionContentItemEntity(
    var goodsId: Int = 0,
    var goodsName: String? = null,
    var goodsThumb: String? = null
)
