package com.kotlin.freak_ec.main.index

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.IError
import com.kotlin.freak_core.net.callback.IFail
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_core.ui.refresh.RefreshHandler
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class IndexDelegate : BottomItemDelegate() {

    @BindView(R2.id.sw_index)
    @JvmField
    var mRefreshLayout: SwipeRefreshLayout? = null

    @BindView(R2.id.rv_index)
    @JvmField
    var mRecyclerView: RecyclerView? = null

    @BindView(R2.id.tb_index)
    @JvmField
    var mToolbar: Toolbar? = null


    @BindView(R2.id.icon_index_scan)
    @JvmField
    var mIconScan: IconTextView? = null

    @BindView(R2.id.ed_search_view)
    @JvmField
    var mSearchView: AppCompatEditText? = null

    private var mRefreshHandler: RefreshHandler? = null

    private fun initRefreshLayout() {
        mRefreshLayout?.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_orange_dark,
            android.R.color.holo_red_light
        )
        mRefreshLayout?.setProgressViewOffset(true, 120, 300)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initRefreshLayout()
        mRefreshHandler?.firstPage()
    }

    override fun setLayout(): Any {
        return R.layout.delegate_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRefreshHandler = RefreshHandler(mRefreshLayout)
        RestClient.builder()
            .url("index")
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val indexDataConverter = IndexDataConverter()

                    val list: ArrayList<MultipleItemEntity> =
                        indexDataConverter.setJsonData(response).convert()

                    val imageUrl = list[1].getField<String>(MutilpleFields.IMAGE_URL)

                    Toast.makeText(context, imageUrl, Toast.LENGTH_LONG).show()
                }

            })
            .error(object : IError {
                override fun onIError(code: Int, message: String) {

                }

            })
            .fail(object : IFail {
                override fun onFail() {

                }

            })
            .build()
            .get()
    }


}