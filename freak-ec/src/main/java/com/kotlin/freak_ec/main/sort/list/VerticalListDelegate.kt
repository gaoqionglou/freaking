package com.kotlin.freak_ec.main.sort.list

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
import com.kotlin.freak_ec.main.sort.SortDelegate

class VerticalListDelegate : FreakDelegate() {

    @BindView(R2.id.rv_vertical_menu_list)
    @JvmField
    var mRecyclerView: RecyclerView? = null

    override fun setLayout(): Any {
        return R.layout.delegate_vertical_list
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = manager
        mRecyclerView?.itemAnimator = null


    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initRecyclerView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient.builder()
            .url(DebugInterceptor.sort_list_url)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val data = VerticalListDataConverter().setJsonData(response).convert()
                    val sortDelegate: SortDelegate = getParentDelegate()
                    val adapter = SortRecyclerAdapter(data, sortDelegate)
                    mRecyclerView?.adapter = adapter
                }
            })
            .build()
            .get()
    }

}