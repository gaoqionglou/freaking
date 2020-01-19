package com.kotlin.freak_core.ui.refresh

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_core.ui.recycler.DataConverter
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter

class RefreshHandler(

    private val REFRESH_LAYOUT: SwipeRefreshLayout?, private val CONVERTER: DataConverter?,
    private val RECYCLERVIEW: RecyclerView?,
    private val BEAN: PagingBean
) :
    SwipeRefreshLayout.OnRefreshListener
    , OnLoadMoreListener {
    override fun onLoadMore() {
        pagging("index?index=")
    }


    private var recyclerListOnItemClickListener: OnItemClickListener? = null


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
            return RefreshHandler(

                refreshLayout,
                dataConverter,
                recyclerView,
                PagingBean()
            )
        }
    }


    fun setListItemOnItemClickListener(listener: OnItemClickListener) {
        this.recyclerListOnItemClickListener = listener
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
                    mAdapter?.setOnItemClickListener(recyclerListOnItemClickListener)
                    mAdapter?.loadMoreModule?.setOnLoadMoreListener(this@RefreshHandler)
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


    private fun pagging(url: String) {
        val pageSize = BEAN.mPageSzie
        val currentCount = BEAN.mCurrentCount
        val total = BEAN.mTotal
        val index = BEAN.mPageIndex
        if (mAdapter?.data?.size ?: 0 < pageSize || currentCount >= total) {
            mAdapter?.loadMoreModule?.loadMoreEnd(true)
        } else {
            Freak.getHandler().postDelayed(object : Runnable {
                override fun run() {
                    RestClient.builder()
                        .url(DebugInterceptor.paging_data_url /*TODO 真实情况要 url+index*/)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String?) {
                                mAdapter?.addData(CONVERTER?.setJsonData(response)?.convert()!!)
                                BEAN.setCurrentCount(mAdapter?.data?.size ?: 0)
                                mAdapter?.loadMoreModule?.loadMoreComplete()
                                BEAN.addIndex()
                            }

                        }).build()
                        .get()

                }

            }, 1000)
        }
    }
}