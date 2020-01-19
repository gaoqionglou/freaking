package com.kotlin.freak_ec.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class GoodsInfoDelegate : FreakDelegate() {

    @BindView(R2.id.tv_goods_info_title)
    @JvmField
    var mGoodsTitle: AppCompatTextView? = null

    @BindView(R2.id.tv_goods_info_desc)
    @JvmField
    var mGoodsDesc: AppCompatTextView? = null

    @BindView(R2.id.tv_goods_info_price)
    @JvmField
    var mGoodsPrice: AppCompatTextView? = null


    var mData: JSONObject? = null

    companion object {
        val ARG_GOODS_DATA = "ARG_GOODS_DATA"
        fun create(goodsInfo: String): GoodsInfoDelegate {
            val args = Bundle()
            args.putString(ARG_GOODS_DATA, goodsInfo)
            val goodsInfoDelegate = GoodsInfoDelegate()
            goodsInfoDelegate.arguments = args
            return goodsInfoDelegate
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val goodsData = arguments?.getString(ARG_GOODS_DATA)
            Toast.makeText(context, "商品数据：$goodsData", Toast.LENGTH_SHORT).show()

            mData = JSON.parseObject(goodsData)

        }
    }


    override fun setLayout(): Any {
        return R.layout.delegate_goods_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val name = mData?.getString("name")
        val desc = mData?.getString("description")
        val price = mData?.getDouble("price")
        mGoodsTitle?.text = name
        mGoodsDesc?.text = desc
        mGoodsPrice?.text = price.toString()
    }

}