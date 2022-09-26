package com.example.server.servers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log

class DeviceInfoProvider: ContentProvider() {
    companion object {
        const val tag = "DeviceInfoProvider"
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
    ): Cursor? {
        Log.d(tag, "uri = $p0")
        val matrixCursor= MatrixCursor(arrayOf("_id", "item", "deviceId"));
        matrixCursor.addRow(arrayListOf(0, "itemValue", "deviceddaf sfaf"));
        Log.d(tag, "uri = xxxx")
        return matrixCursor
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
        return 0
    }
}
