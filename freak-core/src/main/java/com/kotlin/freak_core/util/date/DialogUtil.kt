package com.kotlin.freak_core.util.date


import android.content.Context
import android.content.DialogInterface
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.freak_core.R
import java.text.SimpleDateFormat
import java.util.*

class DialogUtil {

    interface IDateListener {
        fun onDateChange(date: String)
    }


    var dateListener: IDateListener? = null

    fun showDateDialog(context: Context) {
        val ll: LinearLayout = LinearLayout(context)
        val datePicker: DatePicker = DatePicker(context)
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        datePicker.layoutParams = lp

        datePicker.init(1990, 1, 1, object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(
                view: DatePicker?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
                val cal = Calendar.getInstance()
                cal.set(year, monthOfYear, dayOfMonth)
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = format.format(cal.time)
                dateListener?.onDateChange(date)
            }

        })

        ll.addView(datePicker)
        AlertDialog.Builder(context)
            .setTitle("选择日期")
            .setView(ll)
            .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }

            }).setNegativeButton("取消", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }

            }).show()
    }


    fun showAddressAddingDialog(context: Context, addressaddListener: IAddressAddListener?) {
        val ll: LinearLayoutCompat = LayoutInflater.from(context).inflate(
            R.layout.dialog_address_adding_panel,
            null
        ) as LinearLayoutCompat


        val etName =
            ll.findViewById<TextInputEditText>(R.id.dialog_address_adding_et_name)
        val etPhone =
            ll.findViewById<TextInputEditText>(R.id.dialog_address_adding_et_phone)
        val etAddress =
            ll.findViewById<TextInputEditText>(R.id.dialog_address_adding_et_address)

        val dialog = AlertDialog.Builder(context)
            .setTitle("添加地址")
            .setView(ll)
            .setCancelable(true)
            .setPositiveButton("确定", null)
            .setNegativeButton("取消", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }

            }).create()
        dialog.show()

        //dialog的按钮点击默认回消失，这样做为了不让按钮点击一下就消失
//        为了防止 getButton() 为空,先show
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var isPass = checkForm(etName, etPhone, etAddress)


            if (isPass) {
                addressaddListener?.onAddressAdd(
                    etName?.text?.trim().toString(),
                    etPhone?.text?.trim().toString(),
                    etAddress?.text?.trim().toString()
                )
                dialog.cancel()
            } else {

            }
        }


    }

    private fun checkForm(
        etName: TextInputEditText?,
        etPhone: TextInputEditText?,
        etAddress: TextInputEditText?
    ): Boolean {
        val name = etName?.text?.trim().toString()

        val phone = etPhone?.text?.trim().toString()
        val address = etAddress?.text?.trim().toString()
        var isPass = true

        if (name.isEmpty()) {
            etName?.error = "请输入名字"
            isPass = false
        } else {
            etName?.error = null
        }

        if (address.isEmpty()) {
            etAddress?.error = "请输入地址"
            isPass = false
        } else {
            etAddress?.error = null
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            etPhone?.error = "请输入正确的手机"
            isPass = false
        } else {
            etPhone?.error = null
        }
        return isPass
    }


    interface IAddressAddListener {
        fun onAddressAdd(name: String, phone: String, address: String)
    }

}