package com.kotlin.freak_ec.main.cart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class ShopCartDelegate : BottomItemDelegate(), ICartItemListener {



    private val SELECTED_ALL = 1
    private val SELECTED_NOT_ALL = 0
    private var mAdapter: ShopCartAdapter? = null
    //购物车数量标记
    private var mCurrentCount: Int = 0
    private var mTotalCount: Int = 0
    private var mIconSelectedCount = 0

    @BindView(R2.id.iv_shop_cart)
    @JvmField
    var mRecyclerView: RecyclerView? = null

    @BindView(R2.id.icon_shop_cart_select_all)
    @JvmField
    var mIconSelectedAll: IconTextView? = null

    @BindView(R2.id.tv_top_shop_cart_delete)
    @JvmField
    var tvDel: AppCompatTextView? = null

    @BindView(R2.id.tv_top_shop_cart_clear)
    @JvmField
    var tvClear: AppCompatTextView? = null


    @BindView(R2.id.stub_no_item)
    @JvmField
    var mStubNoItem: ViewStub? = null


    @BindView(R2.id.tv_shop_cart_total_price)
    @JvmField
    var tvTotalPrice: AppCompatTextView? = null


    @OnClick(R2.id.icon_shop_cart_select_all)
    fun onClickSelectAll() {
        val tag: Int = mIconSelectedAll?.tag as Int
        if (tag == SELECTED_NOT_ALL) {
            mIconSelectedAll?.setTextColor(
                ContextCompat.getColor(
                    context as Context,
                    R.color.app_main
                )
            )
            mIconSelectedAll?.tag = SELECTED_ALL
            mAdapter?.isSelectedAll = true
            //更新 recycler的显示状态
            mAdapter?.notifyItemChanged(0, mAdapter?.itemCount)

        } else {
            mIconSelectedAll?.setTextColor(Color.GRAY)
            mIconSelectedAll?.tag = SELECTED_NOT_ALL
            mAdapter?.isSelectedAll = false
            //更新 recycler的显示状态
            mAdapter?.notifyItemChanged(0, mAdapter?.itemCount)
        }
    }


    @OnClick(R2.id.tv_top_shop_cart_delete)
    fun onClickRemove() {
        val datas = mAdapter?.data ?: return
        val datasDeleted = ArrayList<MultipleItemEntity>()
        val size = datas.size
        for (i in 0 until size) {
            val data = datas[i]
            if (data.getField(ShopCartItemFields.IS_SELECTED)) {
                datasDeleted.add(data)
            }
        }

        val sizeDeleted = datasDeleted.size
        for (j in 0 until sizeDeleted) {
            var removePosition = 0
            val dataDeleted = datasDeleted[j]
            val entityPosistion: Int = dataDeleted.getField(ShopCartItemFields.POSITION)
            if (entityPosistion > mCurrentCount - 1) {
                removePosition = entityPosistion - (mTotalCount - mCurrentCount)
            } else {
                removePosition = entityPosistion
            }
            if (removePosition <= mAdapter?.itemCount ?: 0) {
                if (removePosition == mAdapter?.itemCount ?: 0) {
                    removePosition = (mAdapter?.itemCount ?: 0) - 1
                }
                //adapter 的data移除掉这个
                mAdapter?.remove(removePosition)
                mCurrentCount = mAdapter?.itemCount ?: 0
                //更新数据
                mAdapter?.notifyItemChanged(removePosition, mAdapter?.itemCount)
            }
            mCurrentCount = mAdapter?.itemCount ?: 0
            mTotalCount = mAdapter?.itemCount ?: 0
        }
        checkItemCount()

    }


    @OnClick(R2.id.tv_top_shop_cart_clear)
    fun onClickClear() {
        mAdapter?.data?.clear()
        mAdapter?.notifyDataSetChanged()
        checkItemCount()
    }

    fun checkItemCount() {
        val itemCount = mAdapter?.itemCount ?: 0
        if (itemCount == 0) {
            val stubView: View? = mStubNoItem?.inflate()
            val tvToBuy: AppCompatTextView? = stubView?.findViewById(R.id.tv_stub_to_buy)
            tvToBuy?.setOnClickListener {
                Toast.makeText(context, "你该购物啦", Toast.LENGTH_SHORT).show()
            }
            mRecyclerView?.visibility = View.GONE
        } else {
            mRecyclerView?.visibility = View.VISIBLE

        }
    }


    override fun setLayout(): Any {
        return R.layout.delegate_shop_cart
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mIconSelectedAll?.tag = SELECTED_NOT_ALL
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        RestClient.builder().url(DebugInterceptor.shop_cart_data_url).loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val data = ShopCartDataConverter().setJsonData(response).convert()
                    val linearLayoutManager = LinearLayoutManager(context)
                    mRecyclerView?.layoutManager = linearLayoutManager
                    mAdapter = ShopCartAdapter(data)
                    mAdapter?.cartItemListener = this@ShopCartDelegate
                    mRecyclerView?.adapter = mAdapter
                    checkItemCount()
                    val totalPrice = mAdapter?.getTotalPrice() ?: 0.0
                    tvTotalPrice?.text = totalPrice.toString()
                }

            }).build().get()
    }


    override fun onItemClick(itemTotalPrice: Double) {

        tvTotalPrice?.text = itemTotalPrice.toString()

    }
}