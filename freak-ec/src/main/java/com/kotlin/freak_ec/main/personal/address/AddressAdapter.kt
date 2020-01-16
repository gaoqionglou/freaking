package com.kotlin.freak_ec.main.personal.address

import androidx.appcompat.widget.AppCompatTextView
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_core.ui.recycler.MultipleViewHolder
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_ec.R

class AddressAdapter(data: ArrayList<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {


    init {
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address)
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            AddressItemType.ITEM_ADDRESS -> {
                helper.setText(R.id.tv_address_name, item?.getField<String>(MutilpleFields.NAME))
                helper.setText(
                    R.id.tv_address_phone,
                    item?.getField<String>(AddressItemFields.PHONE)
                )
                helper.setText(
                    R.id.tv_address_address,
                    item?.getField<String>(AddressItemFields.ADDRESS)
                )

                helper.getView<AppCompatTextView>(R.id.tv_address_delete).setOnClickListener {
                    //请求服务器删除接口。。
                    //recyclerview 执行删除
                    remove(helper.layoutPosition)
                }
            }
        }
    }
}
