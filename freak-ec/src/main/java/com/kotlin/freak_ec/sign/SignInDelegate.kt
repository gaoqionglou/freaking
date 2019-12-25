package com.kotlin.freak_ec.sign

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.OnClick
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class SignInDelegate : FreakDelegate() {

    @BindView(R2.id.delegate_sign_in_ed_email)
    @JvmField
    var signInEdEmail: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_in_ed_password)
    @JvmField
    var signInEdPassword: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_in_btn)
    @JvmField
    var signInBtn: AppCompatButton? = null

    @BindView(R2.id.delegate_sign_in_tips_sign_up)
    @JvmField
    var tipsSignUp: AppCompatTextView? = null


    override fun setLayout(): Any {
        return R.layout.delegate_sign_in
    }

    fun checkForm(): Boolean {

        val email = signInEdEmail?.text?.trim().toString()

        val password = signInEdPassword?.text?.trim().toString()

        var isPass = true

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInEdEmail?.error = "错误邮箱格式"
            isPass = false
        } else {
            signInEdEmail?.error = null
        }

        if (password.isEmpty() || password.length < 6) {
            signInEdPassword?.error = "请输入至少6位密码"
            isPass = false
        } else {
            signInEdPassword?.error = null
        }
        return isPass

    }

    @OnClick(R2.id.delegate_sign_in_btn)
    fun onSignIn() {
        if (checkForm()) {

        }
    }

    @OnClick(R2.id.delegate_sign_in_tips_sign_up)
    fun toSignUp() {
        start(SignUpDelegate())
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }


}