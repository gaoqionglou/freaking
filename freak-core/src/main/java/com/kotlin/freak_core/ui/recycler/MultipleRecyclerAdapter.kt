package com.kotlin.freak_core.ui.recycler


import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.GridLayoutManager
import android.view.View

import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter

import com.kotlin.freak_core.R
import com.kotlin.freak_core.ui.banner.BannerCreator


class MultipleRecyclerAdapter(data: ArrayList<MultipleItemEntity>?) :
    BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>(data),
    BaseQuickAdapter.SpanSizeLookup,
    OnItemClickListener {


    init {
        init()
    }


    //banner的点击事件
    override fun onItemClick(position: Int) {

    }


    //确保初始化一次banner，防止重复加载
    private var mIsInitBanner = false

    companion object {
        fun create(data: ArrayList<MultipleItemEntity>): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(data)
        }

        fun create(converter: DataConverter?): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(converter?.convert())
        }
    }


    private fun init() {
        //设置不同的布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text)
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image)
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_text_image)
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner)
        //设置宽度监听
        setSpanSizeLookup(this)
        openLoadAnimation()
        //多次执行动画
        isFirstOnly(false)

    }


    override fun createBaseViewHolder(view: View): MultipleViewHolder {
        return MultipleViewHolder.create(view)
    }

    override fun convert(holder: MultipleViewHolder, item: MultipleItemEntity?) {
        var text: String?
        var imageUrl: String?
        var bannerImages: ArrayList<String>?
        when (holder.itemViewType) {

            ItemType.TEXT -> {
                text = item?.getField(MutilpleFields.TEXT)
                holder.setText(R.id.text_single, text)
            }

            ItemType.IMAGE -> {
                imageUrl = item?.getField(MutilpleFields.IMAGE_URL)
                Glide.with(mContext)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .centerCrop()
                    .into(holder.getView(R.id.img_single) as AppCompatImageView)
            }

            ItemType.TEXT_IMAGE -> {
                text = item?.getField(MutilpleFields.TEXT)
                holder.setText(R.id.tv_multiple, text)
                imageUrl = item?.getField(MutilpleFields.IMAGE_URL)
                Glide.with(mContext)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .centerCrop()
                    .into(holder.getView(R.id.img_multiple) as AppCompatImageView)
            }

            ItemType.BANNER -> {
                if (!mIsInitBanner) {
                    bannerImages = item?.getField(MutilpleFields.BANNERS)
                    val banner = holder.getView(R.id.banner_recycler) as ConvenientBanner<String>
                    BannerCreator.setDefault(banner, bannerImages, this)
                }
            }

        }
    }

    override fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int {
        return data[position].getField(MutilpleFields.SPAN_SIZE)
    }

}