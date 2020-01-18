package com.kotlin.freak_ec.main.personal.order.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R


class AutoPhotoLayout : LinearLayoutCompat {

    private var mCurrentNum = 0
    //最大允许个数
    private var mMaxNum = 0
    //每行最多个数
    private var mMaxLineNum = 0
    private var mIConAdd: IconTextView? = null
    private var mParams: LinearLayoutCompat.LayoutParams? = null
    //要删除的图片ID
    private var mDeleteId = 0
    //选中的图片
    private var mTargetImageView: AppCompatImageView? = null
    private var mImageMargin = 0
    private var mDelegate: FreakDelegate? = null
    private var mLineViews: ArrayList<View>? = null
    private var mTargetDialog: AlertDialog? = null
    private val ICON_TEXT = "{fa-plus}"
    private var mIconSize = 0f

    private val ALL_VIEWS = ArrayList<ArrayList<View>>()
    //存储每一行的高度
    private val LINE_HEIGHTS = ArrayList<Int>()
    //防止多次的测量和布局过程
    private var mIsOnceInitOnMeasure = false
    private var mHasInitOnLayout = false

    private val OPTIONS = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {

        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.camera_flow_layout)
        mMaxNum = typeArray.getInt(R.styleable.camera_flow_layout_max_count, 1)
        mMaxLineNum = typeArray.getInt(R.styleable.camera_flow_layout_item_margin, 3)
        mImageMargin = typeArray.getInt(R.styleable.camera_flow_layout_item_margin, 0)
        mIconSize = typeArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20F)
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWith = MeasureSpec.getMode(widthMeasureSpec)
        val sizeHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        //wrap_content
        var width = 0
        var height = 0
        //记录每一行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0
        var count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            //测量子ViEW
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            //子view占据的宽度
            var childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            //子view 占据的高度
            var childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行
            if (lineWidth + childWidth > sizeWidth - paddingLeft - paddingRight) {
                width = Math.max(width, lineWidth)
                lineWidth = childWidth
                height += lineHeight
                lineHeight = childHeight
            } else {
                //未换行
                //叠加行宽
                lineWidth += childWidth
                //得到当前最大高度
                lineHeight = Math.max(lineHeight, childHeight)

            }
            //最后一个
            if (i == count - 1) {
                width = Math.max(lineWidth, width)
                //判断是否超过最大拍照限制
                height += lineHeight
            }
        }

        setMeasuredDimension(
            if (modeWith == MeasureSpec.EXACTLY) {
                sizeWidth
            } else {
                width + paddingLeft + paddingRight
            }
            ,
            if (modeHeight == MeasureSpec.EXACTLY) {
                sizeHeight
            } else {
                height + paddingTop + paddingBottom
            }
        )

        //设置一行所有图片的宽高
        if (!mIsOnceInitOnMeasure) {
            val imageTotal =
                sizeWidth - (mMaxLineNum - 1) * mImageMargin - paddingLeft - paddingRight
            val imageSideLen = imageTotal / mMaxLineNum
            mParams = LinearLayoutCompat.LayoutParams(imageSideLen, imageSideLen)
            mIsOnceInitOnMeasure = true
        }

    }

    fun setDelegate(delegate: FreakDelegate) {
        this.mDelegate = delegate
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        ALL_VIEWS.clear()
        LINE_HEIGHTS.clear()
        //当前viewGroup的宽度
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        if (!mHasInitOnLayout) {
            mLineViews = ArrayList<View>()
            mHasInitOnLayout = true
        }

        val count = childCount
        for (i in 0 until count) {

            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (childWidth + lineWidth + lp.rightMargin + lp.leftMargin > width - paddingLeft - paddingRight) {
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight)
                //记录当前一行的view
                mLineViews?.let { ALL_VIEWS.add(it) }
                //重置宽高
                lineWidth = 0
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin
                //重置view集合
                mLineViews?.clear()
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, lineHeight + lp.topMargin + lp.bottomMargin)
            mLineViews?.add(child)
        }

        //处理最后一行
        LINE_HEIGHTS.add(lineHeight)
        mLineViews?.let { ALL_VIEWS.add(it) }
        //设置子View的位置
        var left = paddingLeft
        var top = paddingTop
        //行数
        val lineNum = ALL_VIEWS.size
        for (i in 0 until lineNum) {
            //当前行所有的view
            mLineViews = ALL_VIEWS.get(i)
            lineHeight = LINE_HEIGHTS.get(i)
            val linesize = mLineViews?.size ?: 0
            for (j in 0 until linesize) {
                //遍历给当前这一行的子view 设置坐标 去排列布局
                val child = mLineViews?.get(j)
                if (child?.visibility == View.GONE) {
                    continue
                }
                val lp = child?.layoutParams as MarginLayoutParams
                //坐标
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth - mImageMargin
                val bc = tc + child.measuredHeight
                child.layout(lc, tc, rc, bc)
                left += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }
            //排列完一行子view之后恢复left top递加上一行的lineheight
            left = paddingLeft
            top += lineHeight
        }

        mIConAdd?.layoutParams = mParams
        mHasInitOnLayout = false
    }

    fun onCropTarget(uri: Uri) {
        createNewImageView()
        Glide.with(context).load(uri).apply(OPTIONS).into(mTargetImageView as AppCompatImageView)
    }

    fun createNewImageView() {
        mTargetImageView = AppCompatImageView(context)
        mTargetImageView?.id = mCurrentNum
        mTargetImageView?.layoutParams = mParams
        mTargetImageView?.setOnClickListener { view ->
            mDeleteId = view.id
            mTargetDialog?.show()

            val window = mTargetDialog?.window
            window?.let {
                it.setContentView(R.layout.dialog_image_click_panel)
                it.setGravity(Gravity.BOTTOM)
                it.setWindowAnimations(R.style.anim_panel_up_from_bottom)
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val parmas = it.attributes
                parmas.width = WindowManager.LayoutParams.MATCH_PARENT
                parmas.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
                parmas.dimAmount = 0.5F
                it.attributes = parmas
                it.findViewById<AppCompatButton>(R.id.dialog_image_clicked_btn_delete)
                    .setOnClickListener {
                        val deleImageView = findViewById<AppCompatImageView>(mDeleteId)
                        val animation = AlphaAnimation(1F, 0F)
                        animation.duration = 500
                        animation.repeatCount = 0
                        animation.fillAfter = true
                        animation.startOffset = 0
                        deleImageView.animation = animation
                        animation.start()
                        this@AutoPhotoLayout.removeView(deleImageView)
                        mCurrentNum -= 1
                        if (mCurrentNum < mMaxNum) {
                            mIConAdd?.visibility = View.VISIBLE
                        }
                        mTargetDialog?.cancel()
                    }
                it.findViewById<AppCompatButton>(R.id.dialog_image_clicked_btn_undetermined)
                    .setOnClickListener { mTargetDialog?.cancel() }
                it.findViewById<AppCompatButton>(R.id.dialog_image_clicked_btn_cancel)
                    .setOnClickListener { mTargetDialog?.cancel() }
            }


        }
        this.addView(mTargetImageView)
        mCurrentNum++
        if (mCurrentNum >= mMaxNum) {
            mIConAdd?.visibility = View.GONE
        }
    }

    fun initAddIcon() {
        mIConAdd = IconTextView(context)
        mIConAdd?.text = ICON_TEXT
        mIConAdd?.gravity = Gravity.CENTER
        mIConAdd?.setBackgroundColor(Color.WHITE)
        mIConAdd?.textSize = mIconSize
        mIConAdd?.setBackgroundResource(R.drawable.border_text)
        mIConAdd?.setOnClickListener {
            mDelegate?.startCameraWithCheck()
        }
        this.addView(mIConAdd)

    }

    //    Finalize inflating a view from XML.  This is called as the last phase  of inflation, after all child views have been added.
    override fun onFinishInflate() {
        super.onFinishInflate()
        initAddIcon()
        mTargetDialog = AlertDialog.Builder(context).create()
    }

}