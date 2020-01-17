package com.kotlin.freak_ec.main.personal.settings

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.callback.CallbackManager
import com.kotlin.freak_core.util.callback.CallbackType
import com.kotlin.freak_core.util.callback.IGlobalCallback
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.personal.list.ListAdapter
import com.kotlin.freak_ec.main.personal.list.ListBean
import com.kotlin.freak_ec.main.personal.list.ListItemType


class SettingsDelegate : FreakDelegate(), OnItemClickListener {

    @BindView(R2.id.rv_settings)
    @JvmField
    var rvSettings: RecyclerView? = null

    override fun setLayout(): Any {
        return R.layout.delegate_settings
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val push = ListBean.Builder()
            .setItemType(ListItemType.ITEM_SWITCH)
            .setId(1)
            .setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { p0, isChecked ->
                if (isChecked) {
                    //拿出callback 执行callback
                    val callback =
                        CallbackManager.instance.getCallback(CallbackType.TAG_OPEN_PUSH) as IGlobalCallback<Any>
                    callback.executeCallback("打开推送")
                } else {
                    val callback =
                        CallbackManager.instance.getCallback(CallbackType.TAG_OPEN_PUSH) as IGlobalCallback<Any>
                    callback.executeCallback("关闭推送")
                }
            })
            .setText("消息推送")
            .build()

        val about = ListBean.Builder()
            .setItemType(ListItemType.ITEM_NORMAL)
            .setId(2)
            .setText("关于")
            .setDelegate(InfoDelegate())
            .build()

        val data = ArrayList<ListBean>()
        data.add(push)
        data.add(about)

        val layoutManager = LinearLayoutManager(context)
        val listAdapter = ListAdapter(data)
        listAdapter.setOnItemClickListener(this)
        rvSettings?.layoutManager = layoutManager
        rvSettings?.adapter = listAdapter
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val listAdapter = adapter as ListAdapter
        val bean = listAdapter.data[position]
        when (bean.id) {
            1 -> {

            }
            2 -> {
                start(bean.delegate)
            }
        }
    }
}