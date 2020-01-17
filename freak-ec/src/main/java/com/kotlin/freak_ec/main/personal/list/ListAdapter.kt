package com.kotlin.freak_ec.main.personal.list

import androidx.appcompat.widget.SwitchCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kotlin.freak_ec.R
import de.hdodenhof.circleimageview.CircleImageView

class ListAdapter(data: ArrayList<ListBean>) :
    BaseMultiItemQuickAdapter<ListBean, BaseViewHolder>(data) {

    companion object {
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate()
    }

    init {

        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout)
        addItemType(ListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar)
        addItemType(ListItemType.ITEM_SWITCH, R.layout.arrow_item_switch)
    }

    override fun convert(helper: BaseViewHolder, item: ListBean?) {
        when (helper.itemViewType) {
            ListItemType.ITEM_NORMAL -> {

                helper.setText(R.id.tv_arrow_text, item?.text)
                helper.setText(R.id.tv_arrow_value, item?.value)

            }

            ListItemType.ITEM_AVATAR -> {

                Glide.with(context).load(item?.imageUrl).apply(OPTIONS)
                    .into(helper.getView(R.id.img_arrow_avatar) as CircleImageView)


            }

            ListItemType.ITEM_SWITCH -> {

                helper.setText(R.id.tv_arrow_switch_text, item?.text)
                val switch = helper.getView<SwitchCompat>(R.id.list_item_switch)
                switch.isChecked = true
                switch.setOnCheckedChangeListener(item?.onCheckedChangeListener)
            }
        }
    }


}