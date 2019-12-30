package com.kotlin.freak_core.ui.recycler

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MultipleViewHolder(view: View) : BaseViewHolder(view) {

    companion object{
        fun create(view: View): MultipleViewHolder {
            return MultipleViewHolder(view)
        }
    }
}