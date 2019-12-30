package com.kotlin.freak_core.ui.refresh

import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IFail
import com.kotlin.freak_core.net.callback.ISuccess

class RefreshHandler(private val REFRESH_LAYOUT: SwipeRefreshLayout?) :
    SwipeRefreshLayout.OnRefreshListener {

    init {
        REFRESH_LAYOUT?.setOnRefreshListener(this)
    }


    fun firstPage() {
        RestClient.builder()
            .url("index")
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    Toast.makeText(
                        Freak.getApplication(),
                        "load first page data $response",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
            .error(object : IError {
                override fun onIError(code: Int, message: String) {

                }

            })
            .fail(object : IFail {
                override fun onFail() {

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

    override fun onRefresh() {
//        refresh()
    }

}