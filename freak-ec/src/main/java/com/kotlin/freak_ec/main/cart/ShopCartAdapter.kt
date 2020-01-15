package com.kotlin.freak_ec.main.cart

import android.graphics.Color
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_core.ui.recycler.MultipleViewHolder
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_ec.R

class ShopCartAdapter(data: ArrayList<MultipleItemEntity>?) : MultipleRecyclerAdapter(data) {


    var isSelectedAll = false

    var cartItemListener: ICartItemListener? = null

    var mTotalPrice: Double = 0.0

    companion object {
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate()
    }

    init {
        val size = data?.size ?: 0
        for (i in 0 until size) {
            val a = data?.get(i)
            val price: Double = a?.getField(ShopCartItemFields.PRICE) ?: 0.0
            val count: Int = a?.getField(ShopCartItemFields.COUNT) ?: 0
            val total = price * count.toDouble()
            mTotalPrice += total
        }
        //添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart)
    }


    fun getTotalPrice(): Double {
        return mTotalPrice
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            ShopCartItemType.SHOP_CART_ITEM -> {


                val id: Int = item?.getField(MutilpleFields.ID) as Int
                val thumb: String = item.getField(MutilpleFields.IMAGE_URL)
                val desc: String = item.getField(ShopCartItemFields.DESC)
                val title: String = item.getField(ShopCartItemFields.TITLE)
                val count: Int = item.getField(ShopCartItemFields.COUNT)
                val price: Double = item.getField(ShopCartItemFields.PRICE)


                val imgThumb: AppCompatImageView = helper.getView(R.id.image_item_shop_cart)
                val tvTitle: AppCompatTextView = helper.getView(R.id.tv_item_shop_cart_title)
                val tvDesc: AppCompatTextView = helper.getView(R.id.tv_item_shop_cart_desc)
                val tvPrice: AppCompatTextView = helper.getView(R.id.tv_item_shop_cart_price)
                val tvCount: AppCompatTextView = helper.getView(R.id.tv_item_shop_cart_count)
                val isSelectedIcon: IconTextView = helper.getView(R.id.icon_item_shop_cart)
                val iconMinus: IconTextView = helper.getView(R.id.icon_item_minus)
                val iconPlus: IconTextView = helper.getView(R.id.icon_item_plus)

                tvCount.text = count.toString()
                tvTitle.text = title
                tvDesc.text = desc
                tvPrice.text = price.toString()
                Glide.with(context).load(thumb).apply(OPTIONS).into(imgThumb)

                //在左测勾勾渲染之前改变全选与否状态
                item.setField(ShopCartItemFields.IS_SELECTED, isSelectedAll)

                val isSelected: Boolean = item.getField(ShopCartItemFields.IS_SELECTED)
                //根据数据状态显示左侧勾
                if (isSelected) {
                    isSelectedIcon.setTextColor(
                        ContextCompat.getColor(
                            Freak.getApplication(),
                            R.color.app_main
                        )
                    )
                } else {
                    isSelectedIcon.setTextColor(Color.GRAY)
                }
                //添加左侧勾勾点击事件
                isSelectedIcon.setOnClickListener {
                    val currentSelected: Boolean = item.getField(ShopCartItemFields.IS_SELECTED)
                    if (currentSelected) {
                        isSelectedIcon.setTextColor(Color.GRAY)
                        item.setField(ShopCartItemFields.IS_SELECTED, false)
                    } else {
                        isSelectedIcon.setTextColor(
                            ContextCompat.getColor(
                                Freak.getApplication(),
                                R.color.app_main
                            )
                        )
                        item.setField(ShopCartItemFields.IS_SELECTED, true)
                    }
                }
                //添加加减事件
                iconMinus.setOnClickListener {
                    val currentCount: Int = item.getField(ShopCartItemFields.COUNT)

                    if (tvCount.text.toString().toInt() > 1) {
                        RestClient.builder()
                            .url(DebugInterceptor.shop_cart_data_url)
                            .loader(context)
//                            .params("count", currentCount) todo 导致分类接口获取失败??
                            .success(object : ISuccess {
                                override fun onSuccess(response: String?) {
                                    var countNum = tvCount.text.toString().toInt()
                                    countNum--
                                    tvCount.text = countNum.toString()
                                    mTotalPrice -= price
                                    cartItemListener?.onItemClick(mTotalPrice)
                                }
                            }).build().post()

                    }
                }

                iconPlus.setOnClickListener {
                    val currentCount: Int = item.getField(ShopCartItemFields.COUNT)
                    RestClient.builder()
                        .url(DebugInterceptor.shop_cart_data_url)
                        .loader(context)
                        .params("count", currentCount)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String?) {
                                var countNum = tvCount.text.toString().toInt()
                                countNum++
                                tvCount.text = countNum.toString()
                                mTotalPrice += price
                                cartItemListener?.onItemClick(mTotalPrice)
                            }
                        }).build().post()


                }

            }

        }
    }

}