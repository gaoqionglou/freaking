package com.kotlin.freak_core.ui.refresh


import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter

class RefreshHandler(
    private val REFRESH_LAYOUT: SwipeRefreshLayout?, private val CONVERTER: DataConverter?,
    private val RECYCLERVIEW: RecyclerView?,
    private val BEAN: PagingBean
) :
    SwipeRefreshLayout.OnRefreshListener {


    init {
        REFRESH_LAYOUT?.setOnRefreshListener(this)
    }

    private var mAdapter: MultipleRecyclerAdapter? = null

    companion object {

        fun create(
            refreshLayout: SwipeRefreshLayout?,
            recyclerView: RecyclerView?,
            dataConverter: DataConverter
        ): RefreshHandler {
            return RefreshHandler(refreshLayout, dataConverter, recyclerView, PagingBean())
        }
    }

    fun firstPage() {
        BEAN.setDelayed(1000)
        RestClient.builder()
            .url("index")
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val json = JSON.parseObject(response)
                    BEAN.setTotal(json.getInteger("total"))
                        .setPageSzie(json.getInteger("page_size"))
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER?.setJsonData(response))
                    RECYCLERVIEW?.adapter = mAdapter
                    BEAN.addIndex()
                }

            })
            .build()
            .get()
    }

    fun startRefresh() {
        REFRESH_LAYOUT?.isRefreshing = true
    }

    fun finishRefresh() {
        Freak.getHandler().post {
            REFRESH_LAYOUT?.isRefreshing = false
        }
    }

    fun refresh() {
        REFRESH_LAYOUT?.isRefreshing = true
        Freak.getHandler().post {
            REFRESH_LAYOUT?.isRefreshing = false
        }
    }

    override fun onRefresh() {
        refresh()
    }

}