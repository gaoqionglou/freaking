package com.kotlin.freak_ec.detail


import android.animation.ObjectAnimator
import android.view.View
import com.daimajia.androidanimations.library.BaseViewAnimator


class ScaleUpAnimatior : BaseViewAnimator() {
    override fun prepare(target: View?) {
        animatorAgent.playTogether(
            ObjectAnimator.ofFloat(target, "scaleX", 0.8f, 1.1f, 1.4f, 1.2f, 1f),
            ObjectAnimator.ofFloat(target, "scaleY", 0.8f, 1.1f, 1.4f, 1.2f, 1f)
        )
    }

}