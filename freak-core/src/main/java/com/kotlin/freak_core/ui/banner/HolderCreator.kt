package com.kotlin.freak_core.ui.banner

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator

class HolderCreator :CBViewHolderCreator<ImageHolder>{
    override fun createHolder(): ImageHolder {
        return ImageHolder()
    }

}