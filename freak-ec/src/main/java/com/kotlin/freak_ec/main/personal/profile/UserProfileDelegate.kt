package com.kotlin.freak_ec.main.personal.profile

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.callback.CallbackManager
import com.kotlin.freak_core.util.callback.CallbackType
import com.kotlin.freak_core.util.callback.IGlobalCallback
import com.kotlin.freak_core.util.date.DialogUtil
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2
import com.kotlin.freak_ec.main.personal.list.ListAdapter
import com.kotlin.freak_ec.main.personal.list.ListBean
import com.kotlin.freak_ec.main.personal.list.ListItemType
import com.kotlin.freak_ec.main.personal.settings.NameDelegate
import de.hdodenhof.circleimageview.CircleImageView


class UserProfileDelegate : FreakDelegate(), OnItemClickListener {

    @BindView(R2.id.rv_user_profile)
    @JvmField
    var rvUserProfile: RecyclerView? = null

    private val mGenders = arrayOf("男", "女", "保密")

    override fun setLayout(): Any {
        return R.layout.delegate_user_profile
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val image = ListBean.Builder()
            .setItemType(ListItemType.ITEM_AVATAR)
            .setId(1)
            .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
            .build()

        val name = ListBean.Builder()
            .setItemType(ListItemType.ITEM_NORMAL)
            .setId(2)
            .setText("姓名")
            .setDelegate(NameDelegate())
            .setValue("未设置姓名")
            .build()

        val gender = ListBean.Builder()
            .setItemType(ListItemType.ITEM_NORMAL)
            .setId(3)
            .setText("性别")
            .setValue("未设置性别")
            .build()

        val birth = ListBean.Builder()
            .setItemType(ListItemType.ITEM_NORMAL)
            .setId(4)
            .setText("生日")
            .setValue("未设置生日")
            .build()

        val data = ArrayList<ListBean>()
        data.add(image)
        data.add(name)
        data.add(gender)
        data.add(birth)


        val layoutManager = LinearLayoutManager(context)
        val listAdapter = ListAdapter(data)
        listAdapter.setOnItemClickListener(this)
        rvUserProfile?.layoutManager = layoutManager
        rvUserProfile?.adapter = listAdapter

    }

    fun getGenderDialog(listener: DialogInterface.OnClickListener) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setSingleChoiceItems(mGenders, 0, listener)
        builder.show()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val listAdapter = adapter as ListAdapter
        val bean = listAdapter.data[position]
        when (bean.id) {
            1 -> {

                CallbackManager.instance.addCallback(CallbackType.ON_CROP,
                    object : IGlobalCallback<Uri> {
                        override fun executeCallback(args: Uri) {
                            Log.i("ON_CROP", args.toString())
                            val avatar = view.findViewById<CircleImageView>(R.id.img_arrow_avatar)
                            Glide.with(context as Context).load(args).into(avatar)
                            //执行上传头像网络请求接口
                            //RestClient....upload()
                        }

                    })
                //头像 开始照相机或者选择图片
                startCameraWithCheck()
            }


            2 -> {
                //修改姓名
                val nameDelegate = bean.delegate
                start(nameDelegate)

            }


            3 -> {
                //修改性别
                getGenderDialog(object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val textGender = view.findViewById<AppCompatTextView>(R.id.tv_arrow_value)
                        textGender.text = mGenders[which]
                        dialog?.cancel()
                    }

                })
            }
            4 -> {
                //生日 -时间选择
                val dateDialogUtil = DialogUtil()
                dateDialogUtil.dateListener = object : DialogUtil.IDateListener {
                    override fun onDateChange(date: String) {
                        val textDate = view.findViewById<AppCompatTextView>(R.id.tv_arrow_value)
                        textDate.text = date

                    }

                }
                dateDialogUtil.showDateDialog(context as Context)
            }
        }
    }


}