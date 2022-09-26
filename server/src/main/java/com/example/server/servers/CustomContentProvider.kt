package com.example.server.servers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.AbstractWindowedCursor
import android.database.Cursor
import android.database.CursorWindow
import android.net.Uri
import android.util.Log

class CustomContentProvider : ContentProvider() {

    companion object {
        const val tag = "CustomContentProvider"
        private const val authority = "com.example.server"
        const val windowName = ".servers.CustomContentProvider"


        public val tokenUri = Uri.parse("content://$authority")
    }

    init {

    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor {
        val window = CursorWindow(windowName)
        // 必须先要设置每一行有多少列才能往里面添加数据
        window.setNumColumns(3)
        // 分配每一行的内存地址，建议每次需要新添加一行时新分配一行的内存。
        window.allocRow()
        Log.d(tag, "current rows: ${window.numRows}")
        window.putString("userValue11", 0, 0)
        window.putString("tokenValue11", 0, 1)
        window.putString("baseUrlValue11", 0, 2)
        return CustomWindowedCursor(window)
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 1
    }

}

class CustomWindowedCursor(window: CursorWindow) : AbstractWindowedCursor() {
    init {
        setWindow(window)
    }

    //定义Client拿到的Cursor的行数
    override fun getCount(): Int {
        return window.numRows
    }

    override fun getColumnNames(): Array<String> {
        return arrayOf("user", "token", "base_url")
    }
}