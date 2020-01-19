package com.kotlin.freak_ec.detail

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.kotlin.freak_core.ui.recycler.*
import com.kotlin.freak_ec.R

class RecyclerImageAdapter(data: ArrayList<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {
    init {
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image)
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {

            ItemType.SINGLE_BIG_IMAGE -> {
                val imageView = helper.getView<AppCompatImageView>(R.id.image_rv_item)
                val url = item?.getField<String>(MutilpleFields.IMAGE_URL)
                Glide.with(context).load(url).into(imageView)

            }

        }
    }
}