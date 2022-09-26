package com.example.firstAndroid.functions.logic.content_provider

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity

object ProvidersKeys{
    const val user = "user"
    const val token = "token"
    const val baseUrl = "base_url"
}

@Route(path = "/logic/contentProvider")
class ContentProviderTestActivity : BaseActivity() {

    companion object {
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

        findViewById<View>(R.id.queryActionBook).setOnClickListener {
            Log.d(tag, "queryActionBook click")

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
    }
}