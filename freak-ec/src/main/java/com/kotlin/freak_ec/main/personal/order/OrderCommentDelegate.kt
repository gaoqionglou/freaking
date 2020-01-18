package com.kotlin.freak_ec.main.personal.order

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.OnClick
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.callback.CallbackManager
import com.kotlin.freak_core.util.callback.CallbackType
import com.kotlin.freak_core.util.callback.IGlobalCallback
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.personal.order.widget.AutoPhotoLayout
import com.kotlin.freak_ec.main.personal.order.widget.StarLayout

class OrderCommentDelegate : FreakDelegate() {


    @BindView(R2.id.top_tv_comment_commit)
    @JvmField
    var tvCommit: AppCompatTextView? = null

    @BindView(R2.id.custom_star_layout)
    @JvmField
    var starLayout: StarLayout? = null


    @BindView(R2.id.custom_auto_photo_layout)
    @JvmField
    var photoLayout: AutoPhotoLayout? = null

    @OnClick(R2.id.top_tv_comment_commit)
    fun onClickCommit() {
        Toast.makeText(context, "RANK : ${starLayout?.getStarCount()}", Toast.LENGTH_SHORT).show()
    }

    override fun setLayout(): Any {
        return R.layout.delegate_order_comment
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        photoLayout?.setDelegate(this)
        CallbackManager.instance.addCallback(CallbackType.ON_CROP, object : IGlobalCallback<Uri> {
            override fun executeCallback(args: Uri) {
                photoLayout?.onCropTarget(args)
            }

        })
    }

}