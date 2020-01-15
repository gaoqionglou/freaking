package com.kotlin.freak_ec.main.personal.settings

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R

class NameDelegate : FreakDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_name
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

}