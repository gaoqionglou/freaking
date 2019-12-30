package com.kotlin.freak_core.ui.banner

import android.view.View
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.kotlin.freak_core.R


class BannerCreator {
    companion object {
        fun setDefault(
            convenientBanner: ConvenientBanner<String>,
            banners: ArrayList<String>?,
            clickListener: OnItemClickListener
        ) {
            convenientBanner.setPages(HolderCreator(), banners)
                .setPageIndicator(intArrayOf(R.drawable.dot_core_normal, R.drawable.dot_core_foucs))
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .startTurning(3000)
                .isCanLoop = true

        }
    }
}