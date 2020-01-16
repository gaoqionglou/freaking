package com.kotlin.freak_core.delegates

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.kotlin.freak_core.util.callback.CallbackManager
import com.kotlin.freak_core.util.callback.CallbackType
import com.kotlin.freak_core.util.callback.IGlobalCallback
import com.kotlin.freak_core.util.camera.CameraImageBean
import com.kotlin.freak_core.util.camera.FreakCamera
import com.kotlin.freak_core.util.camera.RequestCodes
import com.yalantis.ucrop.UCrop
import me.yokeyword.fragmentation.SupportFragment
import permissions.dispatcher.*


@RuntimePermissions
abstract class PermissionCheckerDelegate : BaseDelegate() {


    //不是直接的调用方法
    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun startCamera() {
        FreakCamera.start(this)
    }


    fun startCameraWithCheck() {
        //调用自动生成的方法 ->PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck
        startCameraWithPermissionCheck()
    }


    @OnPermissionDenied(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onCameraDenied() {
        Toast.makeText(context, "不允许拍照", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNever() {
        Toast.makeText(context, "永久拒绝❌权限", Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun onCameraRationale(request: PermissionRequest) {
        showRationaleDialog(request)
    }


    //不是直接调用此方法
    private fun showRationaleDialog(request: PermissionRequest) {
        if (context != null) {
            AlertDialog.Builder(context)
                .setPositiveButton("同意使用",
                    DialogInterface.OnClickListener { dialog, which -> request.proceed() })
                .setNegativeButton("拒绝使用",
                    DialogInterface.OnClickListener { dialog, which -> request.cancel() })
                .setCancelable(false)
                .setMessage("权限管理")
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //调用自动生成的方法 ->PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SupportFragment.RESULT_OK) {
            when (requestCode) {
                RequestCodes.TAKE_PHOTO -> {
                    val resultUri = CameraImageBean.mPath as Uri
                    if (context != null) {
                        UCrop.of(resultUri, resultUri).withMaxResultSize(400, 400)
                            .start(context!!, this)
                    }
                }
                RequestCodes.PICK_PHOTO -> {
                    if (data != null) {
                        val pickPath: Uri = data.data
                        //从相册选择后需要有个路径存放剪裁后的图片
                        val pickCropPath: String = FreakCamera.createCropFile().path
                        UCrop.of(pickPath, Uri.parse(pickCropPath)).withMaxResultSize(400, 400)
                            .start(context!!, this)

                    }
                }
                RequestCodes.CROP_PHOTO -> {
                    val cropUri: Uri = data?.let { UCrop.getOutput(it) }!!
                    val callback =
                        CallbackManager.instance.getCallback(CallbackType.ON_CROP) as IGlobalCallback<Uri>
                    callback.executeCallback(cropUri)


                }
                RequestCodes.CROP_ERROR -> {
                    Toast.makeText(context, "剪裁出错", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}