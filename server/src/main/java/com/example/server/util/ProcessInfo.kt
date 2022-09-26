package com.example.server.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Debug
import android.os.Process
import java.io.FileInputStream
import java.io.IOException

class ProcessInfo {
    var name: String? = null//进程的名字
    var icon: Drawable? = null//进程的图标
    var packageName: String? = null//进程的包名
    var isSystem = false//是否为系统应用
    var memSize: Long = 0 //进程的占用的内存
}

object ProcessUtil {
    fun getProcessListInfo(ctx: Context): List<ProcessInfo>? {
        //获取进程相关信息
        val processInfoList: MutableList<ProcessInfo> = ArrayList()
        //1,activityManager管理者对象和PackageManager管理者对象
        val am: ActivityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pm: PackageManager = ctx.getPackageManager()
        //2,获取正在运行的进程的集合
        val runningAppProcesses: List<ActivityManager.RunningAppProcessInfo> = am.runningAppProcesses

        //3,循环遍历上诉集合,获取进程相关信息(名称,包名,图标,使用内存大小,是否为系统进程(状态机))
        for (info in runningAppProcesses) {
            val processInfo = ProcessInfo()
            //4,获取进程的名称 == 应用的包名
            processInfo.packageName = info.processName
            //5,获取进程占用的内存大小(传递一个进程对应的pid数组)
            val processMemoryInfo: Array<Debug.MemoryInfo> =
                am.getProcessMemoryInfo(intArrayOf(info.pid))
            //6,返回数组中索引位置为0的对象,为当前进程的内存信息的对象
            val memoryInfo = processMemoryInfo[0]
            //7,获取已使用的大小
            processInfo.memSize = (memoryInfo.totalPrivateDirty * 1024).toLong()
            try {
                val applicationInfo: ApplicationInfo = pm.getApplicationInfo(processInfo.packageName!!, 0)
                //8,获取应用的名称
                processInfo.name = applicationInfo.loadLabel(pm).toString()
                //9,获取应用的图标
                processInfo.icon = applicationInfo.loadIcon(pm)
                //10,判断是否为系统进程
                processInfo.isSystem = applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == ApplicationInfo.FLAG_SYSTEM
            } catch (e: PackageManager.NameNotFoundException) {
                //需要处理
                processInfo.name = info.processName
//                processInfo.icon = ctx.getResources().getDrawable(R.drawable.ic_launcher)
                processInfo.isSystem = true
                e.printStackTrace()
            }
            processInfoList.add(processInfo)
        }
        return processInfoList
    }

    fun getProcessName(cxt: Context): String? {
        val pid = Process.myPid()
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }

    fun getProcessName2(): String? {
        var `in`: FileInputStream? = null
        try {
            val fn = "/proc/self/cmdline"
            `in` = FileInputStream(fn)
            val buffer = ByteArray(256)
            var len = 0
            var b: Int
            while (`in`.read().also { b = it } > 0 && len < buffer.size) {
                buffer[len++] = b.toByte()
            }
            if (len > 0) {
                val charset = Charsets.UTF_8
                return String(buffer, 0, len, charset)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}