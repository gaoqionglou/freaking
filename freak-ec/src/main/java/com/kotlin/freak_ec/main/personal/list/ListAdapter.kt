package com.kotlin.freak_ec.main.personal.list

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kotlin.freak_ec.R

class ListAdapter(data: ArrayList<ListBean>) :
    BaseMultiItemQuickAdapter<ListBean, BaseViewHolder>(data) {


    init {

        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout)
    }

    override fun convert(helper: BaseViewHolder, item: ListBean?) {
        when (helper.itemViewType) {
            ListItemType.ITEM_NORMAL -> {

                helper.setText(R.id.tv_arrow_text, item?.text)
                helper.setText(R.id.tv_arrow_value, item?.value)

            }
        }
    }

}