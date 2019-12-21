package com.kotlin.freaking

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IFail
import com.kotlin.freak_core.net.callback.ISuccess

class MainDelegate : FreakDelegate() {
    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        test()
    }

    override fun setLayout(): Any {
        return R.layout.delegate_main
    }


    fun test() {
        RestClient.builder()
            .url("https://www.busdmm.icu/star/tyv")
            .loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
//                    Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                }

            })
            .fail(object : IFail {
                override fun onFail() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            .error(object : IError {
                override fun onIError(code: Int, message: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }).build()
            .get()
    }
}