package com.kotlin.freak_core.ui.banner

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageHolder :Holder<String>{
    private var mImageView:AppCompatImageView?=null
    override fun UpdateUI(context: Context?, position: Int, data: String?) {
        Glide.with(context as Context)
            .load(data)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(mImageView as AppCompatImageView)
    }

    override fun createView(context: Context?): View {
        mImageView = AppCompatImageView(context)
        mImageView?.scaleType = ImageView.ScaleType.FIT_XY
        return mImageView as AppCompatImageView

    }

}