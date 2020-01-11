package com.kotlin.freak_ec.main.discover

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_core.delegates.web.WebDelegateImpl
import com.kotlin.freak_ec.R
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class DiscoverDelegate : BottomItemDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_discover
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val delegate = WebDelegateImpl.create("index.html")
        delegate.setTopDelegate(this.getParentDelegate())
        loadRootFragment(R.id.web_discovery_container, delegate)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}