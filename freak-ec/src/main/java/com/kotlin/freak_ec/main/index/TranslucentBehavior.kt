package com.kotlin.freak_ec.main.index

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.kotlin.freak_core.ui.recycler.RgbValue
import com.kotlin.freak_ec.R

class TranslucentBehavior : CoordinatorLayout.Behavior<Toolbar> {


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    //顶部距离
    private var mDistanceY: Int = 0

    companion object {
        //颜色变化速度
        val SHOW_SPEED: Int = 3
    }

    //定义变化颜色
    private val RGB_VALUE = RgbValue(255, 124, 2)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: Toolbar,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.rv_index
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Toolbar,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        //消化该事件
        return true
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Toolbar,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        //增加滑动距离
        mDistanceY += dy
        //toolbar的高度
        val targetHeight = child.bottom
        //当滑动时，距离小于toolbar高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            val scale: Float = mDistanceY.toFloat() / targetHeight
            val alpha = scale * 255
            child.setBackgroundColor(
                Color.argb(
                    alpha.toInt(),
                    RGB_VALUE.red(),
                    RGB_VALUE.green(),
                    RGB_VALUE.blue()
                )
            )
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(
                Color.rgb(
                    RGB_VALUE.red(),
                    RGB_VALUE.green(),
                    RGB_VALUE.blue()
                )
            )
        }

    }
}