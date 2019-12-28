package com.kotlin.freak_core.delegates.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import butterknife.BindView
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.R
import com.kotlin.freak_core.R2
import com.kotlin.freak_core.delegates.FreakDelegate
import kotlinx.android.synthetic.main.delegate_bottom.view.*
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseBottomDelegate : FreakDelegate(), View.OnClickListener {
    private val ITEM_DELEGATES = arrayListOf<BottomItemDelegate>()
    private val TAB_BEANS = arrayListOf<BottomTabBean>()
    private val ITEMS = LinkedHashMap<BottomTabBean, BottomItemDelegate>()
    private var mCurrentDelegate: Int = 0

    private var mIndexDelegate: Int = 0
    private var mClickedColor: Int = Color.RED

    @BindView(R2.id.bottom_bar)
    @JvmField
    var mBottomBar: LinearLayoutCompat? = null


    abstract fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate>

    abstract fun setIndexDelegate(): Int
    abstract fun setClickedColor(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndexDelegate = setIndexDelegate()
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor()
        }
        val builder = ItemBuilder.builder()
        val items = setItems(builder)

        ITEMS.putAll(items)
        ITEMS.forEach {
            TAB_BEANS.add(it.key)
            ITEM_DELEGATES.add(it.value)
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_bottom;
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val size = ITEMS.size

        for (i in 0 until size) {
            LayoutInflater.from(context).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar)
            val item = mBottomBar?.getChildAt(i) as RelativeLayout
            item.tag = i
            item.setOnClickListener(this)
            val itemIcon = item.getChildAt(0) as IconTextView
            val itemTitle = item.getChildAt(1) as AppCompatTextView
            val bean = TAB_BEANS[i]
            itemIcon.text = bean.ICON
            itemTitle.text = bean.TITLE
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickedColor)
                itemTitle.setTextColor(mClickedColor)
            }
        }


        var supportfragmentArr = Array<SupportFragment>(size, { ITEM_DELEGATES[it] })

        loadMultipleRootFragment(
            R.id.delegate_bottom_container,
            mIndexDelegate,
            *supportfragmentArr
        )

    }

    override fun onClick(v: View?) {

    }
}