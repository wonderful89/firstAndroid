package com.example.server.servers

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.annotation.Nullable
import com.qqz.baselib.IPerson

class PersionService : Service() {
    override fun onCreate() {
        Log.d("PersionService", "onCreate ---")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PersionService", "onStartCommand ---")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("PersionService", "onUnbind ---")
        mBinder = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("PersionService", "onDestroy ---")
        super.onDestroy()
    }

    override fun onRebind(intent: Intent?) {
        Log.d("PersionService", "onRebind ---")
        super.onRebind(intent)
    }

    var mBinder: IPerson.Stub? = null

    @Nullable
    override fun onBind(intent: Intent?): IBinder {
        Log.d("PersionService", "onBind ---")
        if (mBinder == null) {
            mBinder= object : IPerson.Stub() {
                @Throws(RemoteException::class)
                override fun eat(food: String): Boolean {
                    Log.d("PersionService", "this is Server")
                    Log.d("PersionService", "Persion eat $food")
                    if (food == "banana") return true
                    if (food == "beef") return true
                    if (food == "water") return false
                    return false
                }
            }
        }
        return mBinder!!
    }
}
