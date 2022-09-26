package com.example.server.servers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.Log

class BookContentProvider: ContentProvider() {
    private val TAG = "BookContentProvider "

    val AUTHORITY = "org.example.server" // 与AndroidManifest保持一致

    val BOOK_CONTENT_URI = Uri.parse("content://$AUTHORITY/book")
    val USER_CONTENT_URI = Uri.parse("content://$AUTHORITY/user")

    val BOOK_URI_CODE = 0
    val USER_URI_CODE = 1

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE)
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE)
    }

//    // 关联Uri和Uri_Code
//    static
//    {
//        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE)
//        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE)
//    }

    private var mContext: Context? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        showLogs("onCreate 当前线程: " + Thread.currentThread().name)
        mContext = context
        initProviderData() // 初始化Provider数据
        return false
    }

    private fun initProviderData() {
        mDb = DbOpenHelper(mContext).writableDatabase
        mDb!!.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME)
        mDb!!.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME)
        mDb!!.execSQL("insert into book values(3,'Android');")
        mDb!!.execSQL("insert into book values(4, 'iOS');")
        mDb!!.execSQL("insert into book values(5, 'HTML5');")
        mDb!!.execSQL("insert into user values(1, 'Spike', 1);")
        mDb!!.execSQL("insert into user values(2, 'Wang', 0);")
    }

    override fun query(
        uri: Uri,
        projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ): Cursor? {
        showLogs("query 当前线程: " + Thread.currentThread().name)
        val tableName = getTableName(uri)
        require(!TextUtils.isEmpty(tableName)) { "Unsupported URI: $uri" }
        return mDb!!.query(
            tableName,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder,
            null
        )
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        showLogs("insert")
        val table = getTableName(uri)
        require(!TextUtils.isEmpty(table)) { "Unsupported URI: $uri" }
        mDb!!.insert(table, null, values)

        // 插入数据后通知改变
        mContext!!.contentResolver.notifyChange(uri, null)
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>?): Int {
        showLogs("delete")
        val table = getTableName(uri)
        require(!TextUtils.isEmpty(table)) { "Unsupported URI: $uri" }
        val count = mDb!!.delete(table, selection, selectionArgs)
        if (count > 0) {
            mContext!!.contentResolver.notifyChange(uri, null)
        }
        return count // 返回删除的函数
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String?>?
    ): Int {
        showLogs("update")
        val table = getTableName(uri)
        require(!TextUtils.isEmpty(table)) { "Unsupported URI: $uri" }
        val row = mDb!!.update(table, values, selection, selectionArgs)
        if (row > 0) {
            mContext!!.contentResolver.notifyChange(uri, null)
        }
        return row // 返回更新的行数
    }

    private fun getTableName(uri: Uri): String? {
        var tableName: String? = null
        when (sUriMatcher.match(uri)) {
            BOOK_URI_CODE -> tableName = DbOpenHelper.BOOK_TABLE_NAME
            USER_URI_CODE -> tableName = DbOpenHelper.USER_TABLE_NAME
            else -> {}
        }
        return tableName
    }

    private fun showLogs(msg: String) {
        Log.e(TAG, msg)
    }
}

class DbOpenHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val CREATE_BOOK_TABLE = ("CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT)")
    private val CREATE_USER_TABLE = ("CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT, sex INT)")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BOOK_TABLE)
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DB_NAME = "book_provider.db"
        const val BOOK_TABLE_NAME = "book"
        const val USER_TABLE_NAME = "user"
        private const val DB_VERSION = 1
    }
}
