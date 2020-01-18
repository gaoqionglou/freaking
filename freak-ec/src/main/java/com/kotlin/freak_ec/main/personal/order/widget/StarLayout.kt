package com.kotlin.freak_ec.main.personal.order.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_ec.R

class StarLayout : LinearLayoutCompat, View.OnClickListener {

    companion object {
        val ICON_UN_SELECT = "{fa-star-o}"
        val ICON_SELECTED = "{fa-star}"
        val STAR_TOTAL_COUNT = 5
        val STARS = ArrayList<IconTextView>()

    }


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initStarIcons()
    }


    fun initStarIcons() {
        for (i in 0 until STAR_TOTAL_COUNT) {
            val star = IconTextView(context)
            star.gravity = Gravity.CENTER
            val lp = LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            lp.weight = 1F
            star.layoutParams = lp
            star.text = ICON_UN_SELECT
            star.setTag(R.id.star_count, i)
            star.setTag(R.id.star_is_select, false)
            star.setOnClickListener(this)
            STARS.add(star)
            addView(star)
        }
    }


    fun getStarCount(): Int {
        var count = 0
        for (i in 0 until STAR_TOTAL_COUNT) {
            val star = STARS[i]
            if (star.getTag(R.id.star_is_select) as Boolean) {
                count++
            }
        }
        return count
    }


    fun selectStar(count: Int) {
        for (i in 0..count) {

            val star = STARS[i]
            star.text = ICON_SELECTED
            star.setTextColor(Color.RED)
            star.setTag(R.id.star_is_select, true)


        }
    }


    fun unSelectStar(count: Int) {
        for (i in 0 until STAR_TOTAL_COUNT) {
            if (i >= count) {
                val star = STARS[i]
                star.text = ICON_UN_SELECT
                star.setTextColor(Color.GRAY)
                star.setTag(R.id.star_is_select, false)
            }

        }
    }

    override fun onClick(v: View?) {
        val star = v as IconTextView
        val count = star.getTag(R.id.star_count) as Int
        val isSelected = star.getTag(R.id.star_is_select) as Boolean
        if (!isSelected) {
            selectStar(count)
        } else {
            unSelectStar(count)
        }
    }

}