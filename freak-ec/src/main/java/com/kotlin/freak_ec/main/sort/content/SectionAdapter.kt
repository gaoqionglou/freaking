package com.kotlin.freak_ec.main.sort.content

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kotlin.freak_ec.R

class SectionAdapter(layoutResId: Int, sectionResId: Int, data: ArrayList<SectionBean>) :
    BaseSectionQuickAdapter<SectionBean, BaseViewHolder>(sectionResId, layoutResId, data) {


    companion object {
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate()
    }


    init {
        setNormalLayout(layoutResId)
        addChildClickViewIds(R.id.more)
    }


    override fun convert(helper: BaseViewHolder, item: SectionBean?) {
        val goodsId = item?.entity?.goodsId
        val goodsName = item?.entity?.goodsName
        val goodsThumb = item?.entity?.goodsThumb
        helper.setText(R.id.tv, goodsName)
        val goodsImageView = helper.getView<AppCompatImageView>(R.id.iv)
        Glide.with(context).load(goodsThumb).apply(OPTIONS).into(goodsImageView)


    }

    override fun convertHeader(helper: BaseViewHolder, item: SectionBean?) {
        helper.setText(R.id.header, item?.headerTitle)
        helper.setVisible(R.id.more, item?.isMore ?: false)
    }

}