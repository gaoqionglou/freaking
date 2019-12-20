package com.kotlin.freaking

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate

class MainDelegate  :FreakDelegate(){
    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun setLayout(): Any {
        return R.layout.delegate_main
    }

}