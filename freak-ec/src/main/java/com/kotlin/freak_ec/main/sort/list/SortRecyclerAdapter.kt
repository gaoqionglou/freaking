package com.kotlin.freak_ec.main.sort.list

import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MultipleRecyclerAdapter
import com.kotlin.freak_ec.main.sort.SortDelegate

class SortRecyclerAdapter(data: ArrayList<MultipleItemEntity>?) : MultipleRecyclerAdapter(data) {

    private var delegate: SortDelegate? = null

    constructor(data: ArrayList<MultipleItemEntity>?, delegate: SortDelegate) : this(data) {
        this.delegate = delegate
        //添加垂直布局

    }
}