package com.kotlin.freak_ec.main.discover

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kotlin.freak_core.delegates.bottom.BottomItemDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.net.interceptors.DebugInterceptor
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.EcBottomDelegate
import com.kotlin.freak_ec.main.personal.order.OrderCommentDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class DiscoverDelegate : BottomItemDelegate(), OnItemClickListener {

    @BindView(R2.id.rv_discover)
    @JvmField
    var mRecyclerView: RecyclerView? = null

    private var mAdapter: DiscoverFoodAdapter? = null

    override fun setLayout(): Any {
        return R.layout.delegate_discover_food
    }


    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient.builder().url(DebugInterceptor.disocver_food_url).loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val data = FoodDataConverter().setJsonData(response).convert()
                    val linearLayoutManager = LinearLayoutManager(context)
                    mRecyclerView?.layoutManager = linearLayoutManager
                    mAdapter = DiscoverFoodAdapter(data)
                    mAdapter?.setOnItemClickListener(this@DiscoverDelegate)
                    mRecyclerView?.adapter = mAdapter
                }

            }).build().get()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val listAdapter = adapter as DiscoverFoodAdapter
        val bean = listAdapter.data[position]
        getParentDelegate<EcBottomDelegate>().start(OrderCommentDelegate())
    }
}