package com.kotlin.freaking.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.ContentFrameLayout
import com.kotlin.freaking.R
import com.kotlin.freaking.delegates.FreakDelegate
import me.yokeyword.fragmentation.SupportActivity


abstract class ProxyActivity : SupportActivity() {
    abstract fun setRootDelegate():FreakDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    private fun initContainer(savedInstanceState: Bundle?){

        val conentFrameLayout:ContentFrameLayout = ContentFrameLayout(this)
        conentFrameLayout.id = R.id.delegate_container
        if(savedInstanceState==null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
        setContentView(conentFrameLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }
}