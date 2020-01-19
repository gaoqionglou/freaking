package com.kotlin.freak_core.delegates.scanner

import android.content.Context
import android.util.AttributeSet
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScanView : ZBarScannerView {
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)


    override fun createViewFinderView(context: Context?): IViewFinder {
        return FreakViewFinderView(context)
    }
}