package com.kotlin.freak_ec.main.personal.order

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_core.ui.recycler.MultipleViewHolder
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_ec.R

class OrderListAdapter(data: ArrayList<MultipleItemEntity>?) : MultipleRecyclerAdapter(data) {
    companion object {
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate()
    }

    init {

        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list)
    }


    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            OrderListItemType.ITEM_ORDER_LIST -> {
                val imageView = helper.getView<AppCompatImageView>(R.id.image_order_list)
                val title = helper.getView<AppCompatTextView>(R.id.tv_order_list_title)
                val price = helper.getView<AppCompatTextView>(R.id.tv_order_list_price)
                val time = helper.getView<AppCompatTextView>(R.id.tv_order_list_time)


                val thumb = item?.getField<String>(MutilpleFields.IMAGE_URL)
                val titleVal = item?.getField<String>(MutilpleFields.TITLE)
                val priceVal = item?.getField<Double>(OrderItemFields.PRICE)
                val timeVal = item?.getField<String>(OrderItemFields.TIME)

                title.text = "商品:$titleVal "
                price.text = "价格:$priceVal "
                time.text = "时间:$timeVal "

                Glide.with(context).load(thumb).apply(OPTIONS).into(imageView)
            }
        }
    }
}