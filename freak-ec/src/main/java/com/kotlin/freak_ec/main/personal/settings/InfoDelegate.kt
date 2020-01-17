package com.kotlin.freak_ec.main.personal.settings

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R

class InfoDelegate : FreakDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

}