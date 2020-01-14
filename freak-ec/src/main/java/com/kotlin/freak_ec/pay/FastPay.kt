package com.kotlin.freak_ec.pay

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R

class FastPay : View.OnClickListener {


    //设置支付回调监听
    private var listener: IAlipayResultListener? = null
    private var mActivity: Activity? = null

    private var mDialog: AlertDialog? = null

    constructor(delegate: FreakDelegate) {
        mActivity = delegate.getProxyActivity()
        mDialog = AlertDialog.Builder(delegate.context as Context).create()

    }

    companion object {
        fun create(delegate: FreakDelegate): FastPay {
            return FastPay(delegate)
        }
    }


    fun beginPayDialog() {
        mDialog?.show()
        val window: Window? = mDialog?.window
        window?.setContentView(R.layout.dialog_pay_panel)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.anim_panel_up_from_bottom)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //设置属性
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window?.attributes = params

        window?.findViewById<IconTextView>(R.id.btn_dialog_pay_alipay)?.setOnClickListener(this)
        window?.findViewById<IconTextView>(R.id.btn_dialog_pay_wechat)?.setOnClickListener(this)
        window?.findViewById<AppCompatButton>(R.id.btn_dialog_pay_cancel)?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_dialog_pay_alipay -> {
                mDialog?.cancel()
            }
            R.id.btn_dialog_pay_wechat -> {
                mDialog?.cancel()
            }
            R.id.btn_dialog_pay_cancel -> {
                mDialog?.cancel()
            }
        }
    }
}