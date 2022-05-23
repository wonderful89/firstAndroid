package com.example.firstAndroid.base.utils

import android.os.Environment
import com.blankj.utilcode.util.Utils
import java.io.File

object FileConstants {
    private val TAG = "FileConstants"

    val APP_PATH = Utils.getApp().getExternalFilesDir(null)!!.toString()

    //    val PUBLIC_PATH = Environment.getExternalStorageDirectory().absolutePath
    val PUBLIC_PATH = Environment.getExternalStorageState()

    val APP_JSON_PATH = "$APP_PATH/json/"
    val APP_BIN_PATH = "$APP_PATH/bin/"

    fun init() {
        File(APP_JSON_PATH).createOrExistsDir()
        File(APP_BIN_PATH).createOrExistsDir()
    }

    inline fun <reified T> getJSONObject(path: String? = null, file: File? = null): T? {
        val metaFile = file ?: File(path)
        metaFile.read2String()
        if (metaFile.exists() && metaFile.isFile) {
            try {
                val jsonStr = metaFile.readText()//JsonHelper.getJsonStr(metaFile.absolutePath)
                if (jsonStr.isNotEmpty()) {
                    return jsonStr.fromJson<T>()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return null
            }
        }
        return null
    }
}