package com.kotlin.freak_ec.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bigkoo.convenientbanner.ConvenientBanner
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.net.RestClient
import com.kotlin.freak_core.net.callback.ISuccess
import com.kotlin.freak_core.ui.banner.HolderCreator
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class GoodsDetailDelegate : FreakDelegate(), AppBarLayout.OnOffsetChangedListener {


    @BindView(R2.id.app_bar_detail)
    @JvmField
    var mAppBarLayout: AppBarLayout? = null

    @BindView(R2.id.collapsing_toolbar_detail)
    @JvmField
    var mCollapsingToolbarLayout: CollapsingToolbarLayout? = null

    @BindView(R2.id.goods_detail_toolbar)
    @JvmField
    var mGoodsDetailToolbar: Toolbar? = null

    @BindView(R2.id.tab_layout)
    @JvmField
    var mTabLayout: TabLayout? = null

    @BindView(R2.id.view_pager)
    @JvmField
    var mViewPager: ViewPager? = null

    @BindView(R2.id.rl_favor)
    @JvmField
    var mRlFavor: IconTextView? = null

    @BindView(R2.id.rl_add_shop_cart)
    @JvmField
    var mRlAddShopCart: IconTextView? = null


    @BindView(R2.id.detail_banner)
    @JvmField
    var mBanner: ConvenientBanner<String>? = null

    @BindView(R2.id.frame_goods_info)
    @JvmField
    var mGoodsInfo: FrameLayout? = null

    var mGoodsId: Int? = -1

    companion object {
        val ARG_GOODS_ID: String = "ARG_GOODS_ID"
        fun create(goodsId: Int): GoodsDetailDelegate {
            val args = Bundle()
            args.putInt(ARG_GOODS_ID, goodsId)
            val goodsDetailDelegate = GoodsDetailDelegate()
            goodsDetailDelegate.arguments = args
            return goodsDetailDelegate
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mGoodsId = arguments?.getInt(ARG_GOODS_ID)
            Toast.makeText(context, "商品🆔：$mGoodsId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_goods_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mCollapsingToolbarLayout?.setContentScrimColor(Color.WHITE)
        mAppBarLayout?.addOnOffsetChangedListener(this)
        initData()
        initTabLayout()
    }

    fun initData() {
        RestClient.builder()
            .url("goods_detail")
            .loader(context)
            .params("goods_id", mGoodsId ?: -1)
            .success(object : ISuccess {
                override fun onSuccess(response: String?) {
                    val data = JSON.parseObject(response).getJSONObject("data")
                    initBanner(data)
                    initGoodsInfo(data)
                    initPager(data)
                }

            }).build().get()
    }

    fun initGoodsInfo(data: JSONObject) {
        val goodsData = data.toJSONString()
        loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(goodsData))
    }

    fun initBanner(data: JSONObject) {
        val array = data.getJSONArray("banners")
        val images = ArrayList<String>()
        val size = array.size
        for (i in 0 until size) {
            images.add(array.getString(i))
        }
        mBanner?.let {
            it.setPages(HolderCreator(), images)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(intArrayOf(R.drawable.dot_normal, R.drawable.dot_foucs))
                .startTurning(3000).isCanLoop = true
        }

    }


    fun initPager(data: JSONObject) {
        val adapter: PagerAdapter = TabPagerAdapter(fragmentManager, data)
        mViewPager?.adapter = adapter
    }

    fun initTabLayout() {
        mTabLayout?.tabMode = TabLayout.MODE_FIXED
        mTabLayout?.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                context ?: Freak.getApplication(), R.color.app_main
            )
        )
        mTabLayout?.tabTextColors = ColorStateList.valueOf(Color.BLACK)
        mTabLayout?.setBackgroundColor(Color.WHITE)
        mTabLayout?.setupWithViewPager(mViewPager)

    }

    override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {

    }
}