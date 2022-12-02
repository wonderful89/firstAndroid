package com.example.server.servers

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class TestService : Service() {

    companion object {
        val tag = "TestService"
    }

    private val mBinder: DownLoaderBinder = DownLoaderBinder()
    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        Log.e(tag,"onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(tag,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(tag,"onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.e(tag,"onDestroy")
        super.onDestroy()
    }
}

internal class DownLoaderBinder : Binder() {
    fun startDownLoad() {
        Log.e("DownLoaderBinder", "Start download")
    }

    val progress: Int
        get() {
            Log.e("DownLoaderBinder", "Get Progress")
            return 0
        }
}