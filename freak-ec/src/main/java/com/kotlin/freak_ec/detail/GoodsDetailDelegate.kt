package com.kotlin.freak_ec.detail

import android.content.Context
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
import butterknife.OnClick
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.YoYo
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
import com.kotlin.freak_ec.animation.BezierAnimation
import com.kotlin.freak_ec.animation.BezierUtil
import de.hdodenhof.circleimageview.CircleImageView

class GoodsDetailDelegate : FreakDelegate(), AppBarLayout.OnOffsetChangedListener,
    BezierUtil.AnimationListener {


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


    @BindView(R2.id.tv_shopping_cart_amount)
    @JvmField
    var mCircleTextViewShopCartAmount: CircleTextView? = null


    @BindView(R2.id.icon_shop_cart)
    @JvmField
    var mIconShopCart: IconTextView? = null
    var mGoodsId: Int? = -1

    var mGoodsThumbUrl: String? = null
    var mShopCount: Int = 0

    companion object {
        val ARG_GOODS_ID: String = "ARG_GOODS_ID"
        val OPTIONS = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_quexing)
            .error(R.drawable.img_quexing).dontAnimate().override(100, 100)

        fun create(goodsId: Int): GoodsDetailDelegate {
            val args = Bundle()
            args.putInt(ARG_GOODS_ID, goodsId)
            val goodsDetailDelegate = GoodsDetailDelegate()
            goodsDetailDelegate.arguments = args
            return goodsDetailDelegate
        }
    }


    @OnClick(R2.id.rl_add_shop_cart)
    fun onClickAddShopCart() {
        val animImage = CircleImageView(context)
        Glide.with(context as Context).load(mGoodsThumbUrl).apply(OPTIONS).into(animImage)
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImage, this)
    }


    fun setShopCount(data: JSONObject) {
        mGoodsThumbUrl = data.getString("thumb")
        if (mShopCount == 0) {
            mCircleTextViewShopCartAmount?.visibility = View.GONE
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
        mCircleTextViewShopCartAmount?.setCircleBackground(Color.RED)
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
                    setShopCount(data)
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

    override fun onAnimationEnd() {
        YoYo.with(ScaleUpAnimatior())
            .duration(500)
            .playOn(mIconShopCart)
        mShopCount++
        mCircleTextViewShopCartAmount?.visibility = View.VISIBLE
        mCircleTextViewShopCartAmount?.text = mShopCount.toString()
    }
}