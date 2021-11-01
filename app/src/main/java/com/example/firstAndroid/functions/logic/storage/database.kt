package com.example.firstAndroid.functions.logic.storage

import android.content.Context
import androidx.room.RoomDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration




@Database(entities = [EntityUser::class], version = 2, ) // exportSchema = false
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?

    companion object {
//        private var INSTANCE: AppDatabase? = null
//        private val sLock = Any()
//        fun getInstance(context: Context): AppDatabase {
//            synchronized(AppDatabase.Companion.sLock) {
//                if (AppDatabase.Companion.INSTANCE == null) {
//                    AppDatabase.Companion.INSTANCE = Room.databaseBuilder(
//                        context.getApplicationContext(),
//                        AppDatabase::class.java, "user.db"
//                    )
//                        .build()
//                }
//                return AppDatabase.Companion.INSTANCE!!
//            }
//        }
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //  创建新的临时表
        database.execSQL( "CREATE TABLE IF NOT EXISTS `user_tmp` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `first_name` TEXT, `last_name` TEXT, `name` TEXT, `FF_street` TEXT, `FF_state` TEXT, `FF_city` TEXT, `FF_post_code` INTEGER)" );
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_user_name` ON `user_tmp` (`name`)")
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_user_last_name` ON `user_tmp` (`last_name`)")
        // 复制数据
        database.execSQL( "INSERT INTO user_tmp (uid, first_name, last_name) SELECT uid, first_name, last_name FROM user" );
        // 删除表结构
        database.execSQL( "DROP TABLE user" );
        // 临时表名称更改
        database.execSQL( "ALTER TABLE user_tmp RENAME TO user" );
    }
}