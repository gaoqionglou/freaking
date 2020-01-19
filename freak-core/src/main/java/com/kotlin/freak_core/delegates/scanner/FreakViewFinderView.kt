package com.kotlin.freak_core.delegates.scanner

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import me.dm7.barcodescanner.core.ViewFinderView

class FreakViewFinderView : ViewFinderView {
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        mSquareViewFinder = true
        mBorderPaint.color = Color.YELLOW
        mLaserPaint.color = Color.GREEN
    }

}