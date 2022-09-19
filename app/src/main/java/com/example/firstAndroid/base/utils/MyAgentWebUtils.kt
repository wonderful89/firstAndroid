package com.example.firstAndroid.base.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.webkit.PermissionRequest
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.AppOpsManagerCompat
import androidx.core.content.ContextCompat
import java.util.*

object MyAgentWebUtils {

    private val requestCode = 999
    var denyPermissionTmp: List<String>? = null
    var requestTemp: PermissionRequest? = null

    fun onRequestPermissionsResult(requestCode2: Int, permissions: Array<out String>, grantResults: IntArray, context: Activity) {
        if (requestCode2 == requestCode) {
            val deny: List<String>? = getDeniedPermissions(context, denyPermissionTmp!!.toList())
            if (deny != null && deny.isEmpty()) {
                requestTemp?.grant(requestTemp?.resources)
            } else {
                requestTemp?.deny()
            }

            requestTemp = null
            denyPermissionTmp = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onPermissionRequest(request: PermissionRequest?, activity: Activity) {
        val resources = request!!.resources
        val resourcesSet: Set<String> = HashSet(listOf(*resources))
        val permissions = ArrayList<String>(resourcesSet.size)
        if (resourcesSet.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
            permissions.add(Manifest.permission.CAMERA)
        }
        if (resourcesSet.contains(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
            permissions.add(Manifest.permission.RECORD_AUDIO)
        }
        if (permissions.isEmpty()) {
            request.grant(resources)
            return
        }

        requestTemp = request

        denyPermissionTmp = getDeniedPermissions(activity, permissions.toList())
        if (denyPermissionTmp == null || denyPermissionTmp!!.isEmpty()) {
            request.grant(resources)
        } else {
            activity.requestPermissions(permissions.toTypedArray(), requestCode)
        }
    }

    private fun getDeniedPermissions(activity: Activity?, permissions: List<String>): List<String>? {
        if (permissions.isEmpty()) {
            return null
        }
        val deniedPermissions: MutableList<String> = ArrayList(permissions.size)
        for (i in permissions.indices) {
            if (!hasPermission(activity!!, permissions[i])) {
                deniedPermissions.add(permissions[i])
            }
        }
        return deniedPermissions
    }

    private fun hasPermission(context: Context, permissions: String): Boolean {
        return hasPermission2(context, listOf<String>(permissions))
    }

    private fun hasPermission2(context: Context, permissions: List<String?>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions) {
            var result = ContextCompat.checkSelfPermission(context, permission!!)
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
            val op = AppOpsManagerCompat.permissionToOp(permission)
            if (TextUtils.isEmpty(op)) {
                continue
            }
            result = AppOpsManagerCompat.noteProxyOp(context, op!!, context.packageName)
            if (result != AppOpsManagerCompat.MODE_ALLOWED) {
                return false
            }
        }
        return true
    }
}