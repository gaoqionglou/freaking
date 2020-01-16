package com.kotlin.freak_core.util.camera

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.blankj.utilcode.util.FileUtils
import com.kotlin.freak_core.R
import com.kotlin.freak_core.delegates.PermissionCheckerDelegate
import com.kotlin.freak_core.util.file.JFileUtil
import java.io.File


/**
 * 相机处理类
 */
class CameraHandler(

    private val DELEGATE: PermissionCheckerDelegate
) : View.OnClickListener {

    private val DIALOG: AlertDialog = AlertDialog.Builder(DELEGATE.context).create()

    fun beginCameraDialog() {
        DIALOG.show()
        val window: Window? = DIALOG.window
        window?.setContentView(R.layout.dialog_camera_panel)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.anim_panel_up_from_bottom)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //设置属性
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        params?.dimAmount = 0.5f
        window?.attributes = params

        window?.findViewById<AppCompatButton>(R.id.photo_dialog_btn_take)?.setOnClickListener(this)
        window?.findViewById<AppCompatButton>(R.id.photo_dialog_btn_native)
            ?.setOnClickListener(this)
        window?.findViewById<AppCompatButton>(R.id.photo_dialog_btn_cancel)
            ?.setOnClickListener(this)
    }


    private fun getPhotoName(): String {
        return JFileUtil.getFileNameByTime("IMG", "jpg")
    }

    fun takePhoto() {
        val currentPhotoName = getPhotoName()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val tmpFile = File(JFileUtil.CAMERA_PHOTO_DIR, currentPhotoName)
        //兼容7。0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentValues = ContentValues(1)
            contentValues.put(MediaStore.Images.Media.DATA, tmpFile.path)
            val uri = DELEGATE.context?.contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            //需要讲uri路径转化为实际路径
            val realFile =
                FileUtils.getFileByPath(JFileUtil.getRealFilePath(DELEGATE.context, uri));
            val realUri = Uri.fromFile(realFile)
            CameraImageBean.mPath = realUri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)


        } else {
            val fileUri = Uri.fromFile(tmpFile)
            CameraImageBean.mPath = fileUri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

        }
        DELEGATE.startActivityForResult(
            intent,
            RequestCodes.TAKE_PHOTO
        )
    }

    fun pickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        DELEGATE.startActivityForResult(
            Intent.createChooser(intent, "选择获取图片的方式"),
            RequestCodes.PICK_PHOTO
        )
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.photo_dialog_btn_take -> {
                takePhoto()
                DIALOG.cancel()
            }
            R.id.photo_dialog_btn_native -> {
                pickPhoto()
                DIALOG.cancel()
            }
            R.id.photo_dialog_btn_cancel -> {
                DIALOG.cancel()
            }
        }
    }

}