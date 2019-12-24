package com.kotlin.freak_ec.launcher

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder

class LauncherHolderCreator : CBViewHolderCreator<LauncherHolder> {
    override fun createHolder(): LauncherHolder {
        return LauncherHolder()
    }


}


class LauncherHolder : Holder<Int> {
    private var mImageView: AppCompatImageView? = null
    override fun UpdateUI(context: Context?, position: Int, data: Int) {
        mImageView?.setBackgroundResource(data)
    }

    override fun createView(context: Context?): View {
        mImageView = AppCompatImageView(context)
        return mImageView as AppCompatImageView
    }


}