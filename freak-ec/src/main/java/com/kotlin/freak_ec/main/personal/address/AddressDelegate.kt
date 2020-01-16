package com.kotlin.freak_ec.main.personal.address

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_core.util.date.DialogUtil
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2


class AddressDelegate : FreakDelegate(), ISuccess {


    @BindView(R2.id.rv_address)
    @JvmField
    var rvAddress: RecyclerView? = null


    @BindView(R2.id.icon_address_add)
    @JvmField
    var icAddAddress: IconTextView? = null

    var adapter: AddressAdapter? = null
    override fun setLayout(): Any {

        return R.layout.delegate_address
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        RestClient.builder().url(DebugInterceptor.address_data_url)
            .loader(context)
            .success(this)
            .build()
            .get()
    }


    override fun onSuccess(response: String?) {
        val data = AddressDataConverter().setJsonData(response).convert()
        adapter = AddressAdapter(data)
        val manager = LinearLayoutManager(context)
        rvAddress?.layoutManager = manager
        rvAddress?.adapter = adapter
    }

    @OnClick(R2.id.icon_address_add)
    fun onClickAddAddress() {

        DialogUtil().showAddressAddingDialog(
            context as Context,
            object : DialogUtil.IAddressAddListener {
                override fun onAddressAdd(name: String, phone: String, address: String) {
                    val newAddress =
                        MultipleItemEntity.builder().setItemType(AddressItemType.ITEM_ADDRESS)
                            .setField(MutilpleFields.NAME, name)
                            .setField(MutilpleFields.TAG, false)
                            .setField(AddressItemFields.PHONE, phone)
                            .setField(AddressItemFields.ADDRESS, address)
                            .build()
                    adapter?.addData(newAddress)
                }

            })

    }

}