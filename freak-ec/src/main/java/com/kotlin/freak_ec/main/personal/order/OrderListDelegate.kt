package com.kotlin.freak_ec.main.personal.order

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.personal.PersonalDelegate

class OrderListDelegate : FreakDelegate() {


    private var mType: String = ""

    @BindView(R2.id.rv_order_list)
    @JvmField
    var rvOrderList: RecyclerView? = null

    override fun setLayout(): Any {
        return R.layout.delegate_order_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        mType = args?.getString(PersonalDelegate.ORDER_TYPE) ?: ""
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient.builder().loader(context)
            .url(DebugInterceptor.order_list_data_url)
            .params("type", mType)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val manager = LinearLayoutManager(context)
                    rvOrderList?.layoutManager = manager
                    val data = OrderListDataConverter().setJsonData(response).convert()
                    val adapter = OrderListAdapter(data)
                    rvOrderList?.adapter = adapter
                }

            }).build().post()
    }
}