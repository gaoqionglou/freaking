package com.kotlin.freak_core.activity


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.ContentFrameLayout
import com.gyf.immersionbar.ImmersionBar
import com.kotlin.freak_core.R
import com.kotlin.freak_core.delegates.FreakDelegate
import me.yokeyword.fragmentation.SupportActivity


abstract class ProxyActivity : SupportActivity() {
    abstract fun setRootDelegate():FreakDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
        ImmersionBar.with(this).transparentStatusBar().init()
    }

    @SuppressLint("RestrictedApi")
    private fun initContainer(savedInstanceState: Bundle?){

        val contentFrameLayout: ContentFrameLayout = ContentFrameLayout(this)
        contentFrameLayout.id = R.id.delegate_container
        if(savedInstanceState==null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
        setContentView(contentFrameLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }
}