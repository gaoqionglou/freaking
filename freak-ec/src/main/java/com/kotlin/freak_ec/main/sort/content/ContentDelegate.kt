package com.kotlin.freak_ec.main.sort.content

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R

class ContentDelegate : FreakDelegate() {


    private var contentId: Int = -1

    companion object {
        @JvmStatic
        private val ARG_CONTENT_ID = "CONTENT_ID"

        fun newInstance(contentId: Int): ContentDelegate {
            val args: Bundle = Bundle()
            args.putInt(ARG_CONTENT_ID, contentId)
            val contentDelegate = ContentDelegate()
            contentDelegate.arguments = args
            return contentDelegate
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentId = arguments?.getInt(ARG_CONTENT_ID) ?: -1
    }

    override fun setLayout(): Any {
        return R.layout.delegate_list_content
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

}