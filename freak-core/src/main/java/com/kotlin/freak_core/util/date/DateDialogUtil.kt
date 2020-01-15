package com.kotlin.freak_core.util.date

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import java.text.SimpleDateFormat
import java.util.*

class DateDialogUtil {

    interface IDateListener {
        fun onDateChange(date: String)
    }


    var dateListener: IDateListener? = null

    fun showDialog(context: Context?) {
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

}