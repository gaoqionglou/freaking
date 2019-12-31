package com.kotlin.freak_core.ui.recycler

import androidx.annotation.ColorInt
import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

class BaseDecoration(@ColorInt var color: Int, var size: Int) : DividerItemDecoration() {
    init {
        setDividerLookup(DividerLookUpImpl(color, size))
    }

    companion object {
        fun create(@ColorInt color: Int, size: Int): BaseDecoration {
            return BaseDecoration(color, size)
        }
    }

}


class DividerLookUpImpl(private val COLOR: Int, private val SIZE: Int) :
    DividerItemDecoration.DividerLookup {
    override fun getHorizontalDivider(position: Int): Divider {
        return Divider.Builder().size(SIZE).color(COLOR).build()
    }

    override fun getVerticalDivider(position: Int): Divider {
        return Divider.Builder().size(SIZE).color(COLOR).build()
    }

}