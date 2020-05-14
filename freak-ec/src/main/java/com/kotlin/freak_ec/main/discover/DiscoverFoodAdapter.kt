package com.kotlin.freak_ec.main.discover

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_core.ui.recycler.MultipleViewHolder
import com.kotlin.freak_ec.R
import de.hdodenhof.circleimageview.CircleImageView


class DiscoverFoodAdapter(data: ArrayList<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {
    init {
        addItemType(FoodItemType.FOOD_ITEM, R.layout.item_discover_food)
    }

    companion object {
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate()
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            FoodItemType.FOOD_ITEM -> {
                helper.setText(R.id.title, item?.getField<String>(FoodItemFields.TITLE))
                helper.setText(R.id.tel, "电话：" + item?.getField<String>(FoodItemFields.TEL))
                helper.setText(R.id.address, "地址：" + item?.getField<String>(FoodItemFields.ADDRESS))
                helper.setText(
                    R.id.commentCount,
                    "评论数：" + item?.getField<String>(FoodItemFields.COMMENT_COUNT) + "条"
                )
                val imgThumb: CircleImageView = helper.getView(R.id.image)
                val thumb: String? = item?.getField(FoodItemFields.IMAGE_URL)
                Glide.with(context).load(thumb).apply(OPTIONS).into(imgThumb)
            }
        }
    }

}