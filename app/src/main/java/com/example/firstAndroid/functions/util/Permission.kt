package com.example.firstAndroid.functions.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object Permission {

    const val tag = "Permission"
    const val REQUEST_CODE = 5

    //定义三个权限
    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE
    ) else arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    //每个权限是否已授
    fun isPermissionGranted(activity: Activity?): Boolean {
        var retValue = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in permission.indices) {
                if (permission[i] == Manifest.permission.MANAGE_EXTERNAL_STORAGE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        // 先判断有没有权限
                        if (Environment.isExternalStorageManager()) {
                            Log.d(tag, "存在存储权限")
                            retValue = true
                        } else {
                            Log.d(tag, "不存在存储权限-去请求")
                            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                            intent.data = Uri.parse("package:" + activity?.packageName)
                            activity?.startActivityForResult(intent, REQUEST_CODE)
                        }
                    }
                } else {
                    val checkPermission = ContextCompat.checkSelfPermission(activity!!, permission[i])
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        retValue = false
                        break
                    }
                }
            }
        } else {
            retValue = true
        }

        return retValue
    }

    fun checkPermission(activity: Activity?): Boolean {
        var retValue = false
        if (isPermissionGranted(activity)) {
            retValue = true
        } else {
            //如果没有设置过权限许可，则弹出系统的授权窗口
            ActivityCompat.requestPermissions(activity!!, permission, REQUEST_CODE)
            retValue = false
        }
        return retValue
    }
}