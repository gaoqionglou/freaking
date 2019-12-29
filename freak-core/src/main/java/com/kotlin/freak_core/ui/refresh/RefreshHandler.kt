package com.kotlin.freak_core.ui.refresh

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.freak_core.app.Freak

class RefreshHandler(private val REFRESH_LAYOUT: SwipeRefreshLayout?) :
    SwipeRefreshLayout.OnRefreshListener {

    init {
        REFRESH_LAYOUT?.setOnRefreshListener(this)
    }


    fun refresh() {
        REFRESH_LAYOUT?.isRefreshing = true
        Freak.getHandler().postDelayed({
            REFRESH_LAYOUT?.isRefreshing = false
        }, 2000)
    }

    override fun onRefresh() {
        refresh()
    }

}