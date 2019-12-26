package com.kotlin.freak_core.net.download

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import com.kotlin.freak_core.app.Freak
import com.kotlin.freak_core.net.callback.IRequest
import com.kotlin.freak_core.net.callback.ISuccess
import okhttp3.ResponseBody
import java.io.File

class SaveFileTask(private val REQUEST: IRequest?, private val SUCCESS: ISuccess?) :
    AsyncTask<Any, Unit, File>() {


    override fun doInBackground(vararg p: Any): File? {
        var downloadDir = p[0] as String
        var exentsion = p[1] as String
        val body: ResponseBody = p[2] as ResponseBody
        val name = p[3] as String
        if (downloadDir.isEmpty()) {
            downloadDir = "freak_downloads"
        }

        if (exentsion.isEmpty()) {
            exentsion = ""
        }
        if (name.isEmpty()) {
            return null
        } else {
            return null
        }
    }

    override fun onPostExecute(result: File?) {
        super.onPostExecute(result)
        SUCCESS?.onSuccess(result?.path)
        REQUEST?.onRequestEnd()
    }


    fun autoInstallApk(file: File) {
        if (file.name.contains("apk")) {
            val install = Intent()
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            install.action = Intent.ACTION_VIEW
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            Freak.getApplication().startActivity(install)
        }
    }
}