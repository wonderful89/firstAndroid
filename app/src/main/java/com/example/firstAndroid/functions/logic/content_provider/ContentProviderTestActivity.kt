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
        public val tokenUri: Uri = Uri.parse("content://com.example.server")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_test)
        bindEvents()
    }

    @SuppressLint("Range")
    private fun bindEvents() {
        findViewById<View>(R.id.queryAction).setOnClickListener {
            Log.d(tag, "queryAction click")

            val cr = Utils.getApp().contentResolver
            var cursor: Cursor? = null

            cursor = cr.query(tokenUri, null, null, null, null)
            if (cursor == null) {
                Log.i(tag, "cursor is null")
                ToastUtils.showShort("cursor is null")
                return@setOnClickListener
            }
            cursor!!.moveToFirst()
            val user = cursor.getString(cursor.getColumnIndex(ProvidersKeys.user))
            val token = cursor.getString(cursor.getColumnIndex(ProvidersKeys.token))
            val baseUrl = cursor.getString(cursor.getColumnIndex(ProvidersKeys.baseUrl))

            Log.i(tag, "user = $user, token = $token, baseUrl = $baseUrl")
            ToastUtils.showShort("user = $user, token = $token, baseUrl = $baseUrl")
        }
    }
}