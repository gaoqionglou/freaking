package com.kotlin.freak_ec.main.sort.content

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import butterknife.BindView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class ContentDelegate : FreakDelegate() {


    @BindView(R2.id.rv_list_content)
    @JvmField
    var mRecyclerView: RecyclerView? = null

    private var contentId: Int = -1

    private var mData: ArrayList<SectionBean>? = null


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
        initRecyclerView()
        initData()
    }


    fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView?.layoutManager = layoutManager
    }


    fun initData() {
        RestClient.builder().url(DebugInterceptor.sort_content_list_url)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    mData = SectionDataConverter().convert(response!!)
                    val sectionAdapter = SectionAdapter(
                        R.layout.item_section_content,
                        R.layout.item_section_header,
                        mData!!
                    )
                    mRecyclerView?.adapter = sectionAdapter
                }

            }).build()
            .get()
    }
}