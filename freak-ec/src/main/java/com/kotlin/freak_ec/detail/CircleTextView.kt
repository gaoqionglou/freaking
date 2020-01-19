package com.kotlin.freak_ec.detail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView


class CircleTextView : AppCompatTextView {

    var PAINT: Paint? = null
    var FILTER: PaintFlagsDrawFilter? = null

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        PAINT = Paint()
        FILTER = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        PAINT?.color = Color.WHITE
        PAINT?.isAntiAlias = true
    }


    fun setCircleBackground(@ColorInt colorInt: Int) {
        PAINT?.color = colorInt
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        val max = Math.max(width, height)
        setMeasuredDimension(max, max)
    }


    override fun draw(canvas: Canvas?) {
        canvas?.drawFilter = FILTER
        canvas?.drawCircle(width / 2 * 1F, height / 2 * 1F, Math.max(width, height) / 2F, PAINT)
        super.draw(canvas)
    }

}