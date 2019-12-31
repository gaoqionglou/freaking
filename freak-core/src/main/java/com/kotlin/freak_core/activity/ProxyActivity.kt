package com.kotlin.freak_core.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.ContentFrameLayout
import com.kotlin.freak_core.R

import com.kotlin.freak_core.delegates.FreakDelegate
import me.yokeyword.fragmentation.SupportActivity
import qiu.niorgai.StatusBarCompat


abstract class ProxyActivity : SupportActivity() {
    abstract fun setRootDelegate():FreakDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
        StatusBarCompat.translucentStatusBar(this, true)
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