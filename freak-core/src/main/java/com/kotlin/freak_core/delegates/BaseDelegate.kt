package com.kotlin.freak_core.delegates


import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment

abstract class BaseDelegate : SwipeBackFragment() {
    @Suppress("SpellCheckingInspection")
    private var mUnbinder: Unbinder? = null
    abstract fun setLayout(): Any

    abstract fun onBindView(@Nullable savedInstanceState: Bundle?, rootView: View)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View? = null
        if (setLayout() is Int) {
            rootView = inflater.inflate(setLayout() as Int, container, false)
        } else if (setLayout() is View) {
            rootView = setLayout() as View
        }

        if (rootView != null) {
            mUnbinder = ButterKnife.bind(this, rootView)
            onBindView(savedInstanceState, rootView)
        }
        return rootView
    }


    fun getProxyActivity(): AppCompatActivity {
        return _mActivity;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder?.unbind()

    }
}