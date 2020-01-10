package com.kotlin.freak_ec.main

import android.graphics.Color
import com.kotlin.freak_core.delegates.bottom.BaseBottomDelegate
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_core.delegates.bottom.BottomTabBean
import com.kotlin.freak_core.delegates.bottom.ItemBuilder
import com.kotlin.freak_ec.main.discover.DiscoverDelegate
import com.kotlin.freak_ec.main.index.IndexDelegate
import com.kotlin.freak_ec.main.sort.SortDelegate

class EcBottomDelegate  : BaseBottomDelegate(){

    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate> {
        val items = LinkedHashMap<BottomTabBean, BottomItemDelegate>()
        items[BottomTabBean("{fa-home}","主页")]= IndexDelegate()
        items[BottomTabBean("{fa-sort}","分类")]=SortDelegate()
        items[BottomTabBean("{fa-compass}", "发现")] = DiscoverDelegate()
        items[BottomTabBean("{fa-shopping-cart}","购物车")]=IndexDelegate()
        items[BottomTabBean("{fa-user}","我的")]=IndexDelegate()
        return builder.addItems (items).build()
    }

    override fun setIndexDelegate(): Int {
        return 0
    }

    override fun setClickedColor(): Int {
        return Color.parseColor("#ffff8800")
    }



}