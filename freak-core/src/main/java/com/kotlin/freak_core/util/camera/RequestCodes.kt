package com.kotlin.freak_core.util.camera

import com.yalantis.ucrop.UCrop

class RequestCodes {
    companion object {
        val TAKE_PHOTO = 4
        val PICK_PHOTO = 5
        val CROP_PHOTO = UCrop.REQUEST_CROP
        val CROP_ERROR = UCrop.RESULT_ERROR
        val SCAN = 7
    }
}