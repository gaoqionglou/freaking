package com.kotlin.freak_ec.main.sort

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.main.sort.content.ContentDelegate
import com.kotlin.freak_ec.main.sort.list.VerticalListDelegate

class SortDelegate : BottomItemDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_sort
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val listDelegate = VerticalListDelegate()
        loadRootFragment(R.id.vertical_list_container, listDelegate)
        replaceLoadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1), false)
    }
}