package com.kotlin.freak_core.util.camera

import android.net.Uri
import com.kotlin.freak_core.delegates.PermissionCheckerDelegate
import com.kotlin.freak_core.util.file.JFileUtil


/**
 * 照相机i调用类
 */
class FreakCamera {

    companion object {

        fun createCropFile(): Uri {
            return Uri.parse(
                JFileUtil.createFile(
                    "crop_image",
                    JFileUtil.getFileNameByTime("IMG", "jpg")
                ).path
            )
        }


        fun start(delegate: PermissionCheckerDelegate) {
            CameraHandler(delegate).beginCameraDialog()
        }

    }

}