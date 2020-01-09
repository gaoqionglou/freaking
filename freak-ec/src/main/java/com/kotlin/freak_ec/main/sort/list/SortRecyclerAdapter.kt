package com.kotlin.freak_ec.main.sort.list

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.ui.recycler.*
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.main.sort.SortDelegate
import com.kotlin.freak_ec.main.sort.content.ContentDelegate

class SortRecyclerAdapter(data: ArrayList<MultipleItemEntity>?) : MultipleRecyclerAdapter(data) {

    private var mDelegate: SortDelegate? = null
    private var mPrePosition: Int = 0

    constructor(data: ArrayList<MultipleItemEntity>?, delegate: SortDelegate) : this(data) {
        this.mDelegate = delegate
        //添加垂直布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list)
    }

    override fun convert(helper: MultipleViewHolder, item: MultipleItemEntity?) {
        super.convert(helper, item)
        when (helper.itemViewType) {
            ItemType.VERTICAL_MENU_LIST -> {
                val text: String? = item?.getField(MutilpleFields.TEXT)
                val isClicked: Boolean? = item?.getField(MutilpleFields.TAG)
                val name: AppCompatTextView = helper.getView(R.id.tv_vertical_item_name)
                val line: View = helper.getView(R.id.view_line)
                helper.setText(R.id.tv_vertical_item_name, text)
                helper.itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val currentPosition = helper.adapterPosition
                        if (currentPosition != mPrePosition) {
                            //还原上一个
                            data[mPrePosition].setField(MutilpleFields.TAG, false)
                            notifyItemChanged(mPrePosition)

                            //更新选中的
                            item?.setField(MutilpleFields.TAG, true)
                            notifyItemChanged(currentPosition)
                            mPrePosition = currentPosition

                            val contentId = data[currentPosition].getField(MutilpleFields.ID) as Int
                            showContent(contentId)
                        }
                    }

                })


                if (!(isClicked as Boolean)) {
                    //未选中状态
                    line.visibility = View.INVISIBLE
                    name.setTextColor(ContextCompat.getColor(context, R.color.we_chat_black))
                    helper.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.item_background
                        )
                    )
                } else {
                    line.visibility = View.VISIBLE
                    name.setTextColor(ContextCompat.getColor(context, R.color.app_main))
                    line.setBackgroundColor(ContextCompat.getColor(context, R.color.app_main))
                    helper.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.white
                        )
                    )
                }

            }
        }
    }


    fun showContent(contentId: Int) {
        val contentDelegate = ContentDelegate.newInstance(contentId)
        switchContent(contentDelegate)
    }

    private fun switchContent(delegate: ContentDelegate) {
        val contentDelegate: FreakDelegate? =
            mDelegate?.findChildFragment(ContentDelegate::class.java)
        contentDelegate?.replaceFragment(delegate, false)
    }

}