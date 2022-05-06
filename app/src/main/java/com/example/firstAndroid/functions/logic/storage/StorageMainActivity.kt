package com.example.firstAndroid.functions.logic.storage

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alibaba.android.arouter.launcher.ARouter
import com.example.firstAndroid.databinding.ActivityStorageMainBinding
//import com.amitshekhar.DebugDB
import com.example.firstAndroid.functions.logic.LogicTest
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.HalfSerializer.onNext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.sql.Date
import java.time.LocalDateTime

/**
 * https://www.jianshu.com/p/7354d5048597
 * https://github.com/amitshekhariitbhu/Android-Debug-Database
 * 数据库访问点：
 * 1. 命令：`adb forward tcp:808 tcp:8089`
 * 2. 将网址设置成 http://localhost:8089
 */
@Route(path = "/logic/storage")
class StorageMainActivity : BaseActivity() {
    companion object {
        const val tag = "Storage"

    }

    private lateinit var db: AppDatabase
    private var userList: List<EntityUser?>? = null
    private lateinit var binding: ActivityStorageMainBinding

    var counter = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val url = DebugDB.getAddressLog();
        val url = "aaa";
        Log.w(tag, "debug db url = $url")
//        setContentView(R.layout.activity_storage_main)
        binding = ActivityStorageMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("title")

        /**
         * java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time
         * .allowMainThreadQueries()
         */
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "default-database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        initViews()
        refreshData()
        val currentDBPath = getDatabasePath("XXX.db").absolutePath
        Log.w(tag, "currentDBPath = $currentDBPath")

        val path = getDatabasePath(".default-database.db").absolutePath
        if (File(path).exists()) {
            Log.i("currentDBPath", "存在")
        } else {
            Log.i("currentDBPath", "不存在")
        }
    }

    private fun initViews() {
        binding.insertBtn.setOnClickListener {
            runOnIoThread {
                counter++
                var user1 = EntityUser()
                user1.firstName = "firstName_$counter"
                user1.lastName = "last_Name_$counter"
//            val current = LocalDateTime.now()
                val current = System.currentTimeMillis()
                user1.address = Address()
                user1.address!!.state = "China"
                user1.address!!.street = "Street 5"
                user1.address!!.city = "Beijing"
                user1.address!!.postCode = 100080
                db.userDao()!!.insert(user1)
                refreshData()
                Log.d(tag, "插入完成")
            }
        }
    }




    private fun getObserver(): Observer<List<EntityUser?>> {
        return object : Observer<List<EntityUser?>> {
            override fun onSubscribe(d: Disposable) {
                Log.d(tag, "onSubscribe")
            }

            override fun onNext(t: List<EntityUser?>) {
                Log.d(tag, "onNext: $t")
            }


            override fun onError(e: Throwable) {
                Log.e(tag, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(tag, "onComplete")
            }
        }
    }

    private fun getEntityUserObserver(): Observer<EntityUser> {
        return object : Observer<EntityUser> {
            override fun onSubscribe(d: Disposable) {
                Log.d(tag, "onSubscribe")
            }

            override fun onNext(t: EntityUser) {
                Log.d(tag, "onNext: $t")
            }


            override fun onError(e: Throwable) {
                Log.e(tag, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(tag, "onComplete")
            }
        }
    }


    @SuppressLint("CheckResult")
    private fun refreshData() {
        runOnIoThread {
            var all: List<EntityUser?>? = db.userDao()!!.all

            db.userDao()!!.flowFindById(10).observeOn(AndroidSchedulers.mainThread()).subscribe {
                Log.d(tag, "flowFindById observe = $it")
            }

            db.userDao()!!.flowAll.subscribe {
                Log.d(tag, "flowAll observe = $it")
            }

            userList = all
//        val lists = arrayOf("111", "2222", "333")
            val lists = userList?.let{
                it.map { i: EntityUser? -> "${i!!.firstName} ${i.uid}, ${i.address!!.city}" }
            }
            runOnUiThread {
                val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists!!)
                binding.listView2.adapter = arrayAdapter2
                binding.listView2.dividerHeight = 2
                binding.listView2.setSelector(R.drawable.listview_selector_0)

                binding.listView2.setOnItemClickListener { _, _, position: Int, _: Long ->
                    AlertDialog.Builder(this)
                        .setMessage("确定删除")
                        .setTitle("对话框")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            runOnIoThread {
                                val user = userList?.get(position)
                                var user2 = EntityUser()
                                user2.uid = user!!.uid
                                db.userDao()!!.delete(user2)
                                refreshData()
                            }
                        })
                        .setNeutralButton("取消", null)
                        .create()
                        .show()
                }
                Toast.makeText(this,"加载成功",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
