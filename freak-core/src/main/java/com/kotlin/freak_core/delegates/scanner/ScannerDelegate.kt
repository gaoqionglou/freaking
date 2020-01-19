package com.kotlin.freak_core.delegates.scanner

import android.os.Bundle
import android.view.View
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.callback.CallbackManager
import com.kotlin.freak_core.util.callback.CallbackType
import com.kotlin.freak_core.util.callback.IGlobalCallback
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerDelegate : FreakDelegate(), ZBarScannerView.ResultHandler {
    override fun handleResult(result: Result?) {

        val callback =
            CallbackManager.instance.getCallback(CallbackType.ON_SCAN) as IGlobalCallback<String>?
        callback?.executeCallback(result?.contents ?: "")
        pop()
    }


    var mScanView: ScanView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mScanView == null) {
            mScanView = ScanView(context)
        }
        mScanView?.setAutoFocus(true)
        mScanView?.setResultHandler(this)
    }


    override fun setLayout(): Any {
        return mScanView as ScanView
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun onResume() {
        super.onResume()
        mScanView?.startCamera()

    }

    override fun onPause() {
        super.onPause()
        mScanView?.stopCameraPreview()
        mScanView?.stopCamera()
    }
}