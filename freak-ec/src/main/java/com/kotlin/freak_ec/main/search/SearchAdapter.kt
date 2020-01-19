package com.kotlin.freak_ec.main.search

import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_core.ui.recycler.MultipleViewHolder
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_ec.R

class SearchAdapter(data: ArrayList<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {




    init {
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search)
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            SearchItemType.ITEM_SEARCH -> {
                helper.setText(R.id.tv_search_item, item?.getField<String>(MutilpleFields.TEXT))
            }
        }

    }
}