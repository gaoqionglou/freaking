package com.kotlin.freak_ec.sign


import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatButton
import android.util.Patterns
import android.view.View

import butterknife.BindView
import butterknife.OnClick


import com.kotlin.freak_core.app.AccountManager
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.database.bean.UserProfile


class SignUpDelegate : FreakDelegate() {

    @BindView(R2.id.delegate_sign_up_ed_name)
    @JvmField
    var signUpEdName: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_up_ed_email)
    @JvmField
    var signUpEdEmail: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_up_ed_phone)
    @JvmField
    var signUpEdPhone: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_up_ed_password)
    @JvmField
    var signUpEdPassword: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_up_ed_repeat_password)
    @JvmField
    var signUpEdRepeatPassword: TextInputEditText? = null

    @BindView(R2.id.delegate_sign_up_btn_sign_up)
    @JvmField
    var signUpBtn: AppCompatButton? = null

    var iSignLIstener: ISignLIstener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignLIstener) {
            iSignLIstener = activity
        }
    }

    fun checkForm(): Boolean {
        val name = signUpEdName?.text?.trim().toString()
        val email = signUpEdEmail?.text?.trim().toString()
        val phone = signUpEdPhone?.text?.trim().toString()
        val password = signUpEdPassword?.text?.trim().toString()
        val repeatPassword = signUpEdRepeatPassword?.text?.trim().toString()
        var isPass = true
        if (name.isEmpty()) {
            signUpEdName?.error = "请输入姓名"
            isPass = false
        } else {
            signUpEdName?.error = null
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEdEmail?.error = "错误邮箱格式"
            isPass = false
        } else {
            signUpEdEmail?.error = null
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            signUpEdPhone?.error = "错误手机格式"
            isPass = false
        } else {
            signUpEdPhone?.error = null
        }

        if (password.isEmpty() || password.length < 6) {
            signUpEdPassword?.error = "请输入至少6位密码"
            isPass = false
        } else {
            signUpEdPassword?.error = null
        }

        if (repeatPassword.isEmpty() || repeatPassword.length < 6 || !repeatPassword.equals(password)) {
            signUpEdRepeatPassword?.error = "密码验证错误"
            isPass = false
        } else {
            signUpEdRepeatPassword?.error = null
        }

        return isPass

    }

    override fun setLayout(): Any {
        return R.layout.delegate_sign_up
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }


    @OnClick(R2.id.delegate_sign_up_btn_sign_up)
    fun onSignUpClick() {
        if (checkForm()) {
            val userProfile = UserProfile()
            val name = signUpEdName?.text?.trim().toString()
            val email = signUpEdEmail?.text?.trim().toString()
            val phone = signUpEdPhone?.text?.trim().toString()
            val password = signUpEdPassword?.text?.trim().toString()
            userProfile.email = email
            userProfile.name = name
            userProfile.password = password
            SignHandler.signUp(userProfile)

            AccountManager.setLoginStatus(true)
            iSignLIstener?.onSignUpSuccess()

        }
    }

}