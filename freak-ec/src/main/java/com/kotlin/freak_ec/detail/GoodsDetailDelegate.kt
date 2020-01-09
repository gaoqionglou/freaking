package com.kotlin.freak_ec.detail

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R

class GoodsDetailDelegate : FreakDelegate() {

    companion object {
        fun create(): GoodsDetailDelegate {
            return GoodsDetailDelegate()
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_goods_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

}