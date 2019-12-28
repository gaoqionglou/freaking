package com.kotlin.freak_core.delegates.bottom

class ItemBuilder {
    val ITEMS = LinkedHashMap<BottomTabBean, BottomItemDelegate>()
    companion object {
        fun builder():ItemBuilder{
            return ItemBuilder()
        }
    }

    fun addItem(bean: BottomTabBean,delegate: BottomItemDelegate):ItemBuilder{
        ITEMS[bean]=delegate
        return this
    }


    fun addItems(item:LinkedHashMap<BottomTabBean, BottomItemDelegate>):ItemBuilder{
        ITEMS.putAll(item)
        return this
    }

    fun build():LinkedHashMap<BottomTabBean, BottomItemDelegate>{
        return ITEMS
    }


}


class BottomTabBean(
    val ICON: CharSequence? = null,
    val TITLE: CharSequence? = null
) {

}