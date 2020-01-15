package com.kotlin.freak_ec.main.personal

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.personal.list.ListAdapter
import com.kotlin.freak_ec.main.personal.list.ListBean
import com.kotlin.freak_ec.main.personal.list.ListItemType
import com.kotlin.freak_ec.main.personal.order.OrderListDelegate

class PersonalDelegate : BottomItemDelegate() {


    @BindView(R2.id.rv_personal_setting)
    @JvmField
    var rvSettings: RecyclerView? = null

    @BindView(R2.id.tv_all_order)
    @JvmField
    var tvAllOrder: AppCompatTextView? = null


    @BindView(R2.id.tv_all_account_arrow)
    @JvmField
    var iconArrowAll: IconTextView? = null


    companion object {
        val ORDER_TYPE = "ORDER_TYPE"
    }

    private val mArgs: Bundle? = null

    override fun setLayout(): Any {
        return R.layout.delegate_personal
    }

    @OnClick(R2.id.tv_all_order, R2.id.tv_all_account_arrow)
    fun onClickAllOrder() {
        mArgs?.putString(ORDER_TYPE, "all")
        startOrderListDelegateByType()
    }


    fun startOrderListDelegateByType() {
        val orderListDelegate = OrderListDelegate()
        orderListDelegate.arguments = mArgs
        start(orderListDelegate)
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val address = ListBean.Builder().setItemType(ListItemType.ITEM_NORMAL)
            .setId(1)
            .setText("收货地址")
            .setDelegate(this)
            .build()
        val system = ListBean.Builder().setItemType(ListItemType.ITEM_NORMAL)
            .setId(2)
            .setText("系统设置")
            .setDelegate(this)
            .build()


        val beanList = ArrayList<ListBean>()
        beanList.add(address)
        beanList.add(system)


        val layoutmanager = LinearLayoutManager(context)
        rvSettings?.layoutManager = layoutmanager
        val adapter = ListAdapter(beanList)
        rvSettings?.adapter = adapter

    }

}