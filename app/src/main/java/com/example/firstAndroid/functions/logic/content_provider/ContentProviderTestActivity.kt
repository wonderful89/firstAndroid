package com.example.firstAndroid.functions.logic.content_provider

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.qqz.baselib.IPerson




object ProvidersKeys{
    const val user = "user"
    const val token = "token"
    const val baseUrl = "base_url"
}

@Route(path = "/logic/contentProvider")
class ContentProviderTestActivity : BaseActivity() {

    companion object {
    }

    private var mService: IPerson? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) { //绑定Service成功后执行
            Log.d(tag, "onServiceConnected")
            mService = IPerson.Stub.asInterface(iBinder) //获取远程服务的代理对象，一个IBinder对象
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            Log.d(tag, "onServiceDisconnected")
            mService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_test)
        bindEvents()
    }

    @SuppressLint("Range", "Recycle")
    private fun bindEvents() {
        findViewById<View>(R.id.queryAction).setOnClickListener {
            Log.d(tag, "queryAction click")

            val contentResolver = Utils.getApp().contentResolver
            var cursor: Cursor? = null

            cursor = contentResolver.query(Uri.parse("content://com.example.server.custom.provider"), null, null, null, null)
            if (cursor == null) {
                Log.i(tag, "cursor is null")
                ToastUtils.showShort("cursor is null")
                return@setOnClickListener
            }
            cursor.moveToFirst()
            val user = cursor.getString(cursor.getColumnIndex(ProvidersKeys.user))
            val token = cursor.getString(cursor.getColumnIndex(ProvidersKeys.token))
            val baseUrl = cursor.getString(cursor.getColumnIndex(ProvidersKeys.baseUrl))

            Log.i(tag, "user = $user, token = $token, baseUrl = $baseUrl")
            ToastUtils.showShort("user = $user, token = $token, baseUrl = $baseUrl")
            cursor.close()
        }

        findViewById<View>(R.id.queryActionDeviceInfo).setOnClickListener {
            Log.d(tag, "queryActionDeviceInfo click")
            val contentResolver = Utils.getApp().contentResolver
            val cursor = contentResolver.query(Uri.parse("content://com.example.server.device_info.provider"), null, null, null, null)
            if (cursor == null) {
                Log.i(tag, "cursor is null")
                ToastUtils.showShort("cursor is null")
                return@setOnClickListener
            }
            cursor!!.moveToFirst()
            val deviceId = cursor!!.getString(cursor!!.getColumnIndex("deviceId"))

            val msg = "deviceId = $deviceId, "
            Log.i(tag, "$msg")
            ToastUtils.showShort(msg)
            cursor!!.close()
        }

        findViewById<View>(R.id.queryActionBook).setOnClickListener {
            Log.d(tag, "queryActionBook click")

            val contentResolver = Utils.getApp().contentResolver
            val cursor = contentResolver.query(Uri.parse("content://com.example.server.book.provider/book"), null, null, null, null)
            if (cursor == null) {
                Log.i(tag, "cursor is null")
                ToastUtils.showShort("cursor is null")
                return@setOnClickListener
            }
            val nameList = mutableListOf<String>()
            while (cursor!!.moveToNext()) {
                val bookName = cursor!!.getString(cursor!!.getColumnIndex("name"))
                nameList.add(bookName)
            }
            val msg = "nameList = $nameList "
            Log.i(tag, "$msg")
            ToastUtils.showShort(msg)
            cursor!!.close()
        }

        findViewById<View>(R.id.queryActionUser).setOnClickListener {
            Log.d(tag, "queryActionUser click")

            val contentResolver = Utils.getApp().contentResolver
            val cursor = contentResolver.query(Uri.parse("content://com.example.server.book.provider/user"), null, null, null, null)
            if (cursor == null) {
                Log.i(tag, "cursor is null")
                ToastUtils.showShort("cursor is null")
                return@setOnClickListener
            }
            val nameList = mutableListOf<String>()
            while (cursor!!.moveToNext()) {
                val bookName = cursor!!.getString(cursor!!.getColumnIndex("name"))
                nameList.add(bookName)
            }
            val msg = "nameList = $nameList "
            Log.i(tag, "$msg")
            ToastUtils.showShort(msg)
            cursor!!.close()
        }

        findViewById<View>(R.id.queryJiuXueDeviceId).setOnClickListener {
            Log.d(tag, "queryJiuXueDeviceId click")

            val contentResolver = Utils.getApp().contentResolver
            val cursor = contentResolver.query(Uri.parse("content://com.tencent.landmoon.device_info.provider"), null, null, null, null)
                ?: return@setOnClickListener
            cursor!!.moveToFirst()
            val deviceId = cursor!!.getString(cursor!!.getColumnIndex("deviceId"))

            ToastUtils.showShort("deviceId = $deviceId")
            cursor!!.close()
        }

        val persionServiceIntent = Intent().apply {
            setAction("com.example.server.PersionService")
            setPackage("com.example.server")
        }

        findViewById<View>(R.id.bindPersonService).setOnClickListener {
            Log.d(tag, "bindPersonService click")
            bindService(persionServiceIntent, mConnection, BIND_AUTO_CREATE)
        }

        findViewById<View>(R.id.unbindPersonService).setOnClickListener {
            Log.d(tag, "unbindPersonService click")
            mService?.let {
                unbindService(mConnection)
            }
        }

        findViewById<View>(R.id.startPersonService).setOnClickListener {
            Log.d(tag, "startPersonService click")
            startService(persionServiceIntent)
        }
        findViewById<View>(R.id.stopPersonService).setOnClickListener {
            Log.d(tag, "stopPersonService click")
            stopService(persionServiceIntent)
        }
        findViewById<View>(R.id.eatFoot).setOnClickListener {
            Log.d(tag, "eatFoot click")
            try {
                val v1 = mService?.eat("banana")
                val v2 = mService?.eat("water")
                Log.e(tag, "v1 = $v1, v2 = $v2")
            } catch (e: RemoteException) {
                Log.e(tag, "eatFoot exception: $e")
            }
        }
    }
}