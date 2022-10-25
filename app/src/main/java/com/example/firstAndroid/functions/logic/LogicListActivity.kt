package com.example.firstAndroid.functions.logic

import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.example.firstAndroid.R
import com.example.firstAndroid.TestListActivity
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.base.utils.FileConstants
import com.example.firstAndroid.databinding.ActivityTestListBinding
import com.example.firstAndroid.functions.logic.purefunction.JavaDemo
import com.example.firstAndroid.functions.logic.purefunction.WeakRef
import com.example.firstAndroid.functions.logic.purefunction.entry
import java.lang.Exception
import java.lang.ref.Reference
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*

//import kotlinx.android.synthetic.main.activity_test_list.*

class LogicListActivity : BaseActivity() {
    companion object {
        const val tag = "LogicTestList"
    }

    private lateinit var binding: ActivityTestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lists = LogicTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentName: String? = null
            var pos: LogicTest = LogicTest.values()[position]
//            intentName = when (position) {
//                LogicTest.Network.ordinal -> "/logic/network"
//                LogicTest.Storage.ordinal -> "/logic/storage"
//                else -> "/ui/animation2"
//            }
            intentName = pos.path
            val name = intentName.split("/".toRegex()).lastOrNull() ?: "Unknown"
            if (intentName.contains("pureFunction")) {
                entry()
                return@setOnItemClickListener
            }

            if (intentName.contains("Hprof")) {
                dumpHprof()
                return@setOnItemClickListener
            }
            if (intentName.contains("WeakRef")) {
                WeakRef.test()
                return@setOnItemClickListener
            }
            ARouter.getInstance().build(intentName)
                .withString("title", name)
                .navigation()
//            val intent = android.content.Intent(this, intentClass)
//            startActivity(intent)
        }
    }

    private fun dumpHprof() {
        val time = System.currentTimeMillis()
        val filePath = FileConstants.APP_PATH + "/${time.toString()}.hprof"
        LogUtils.dTag(tag, "dumpHprof path = $filePath")
        Debug.dumpHprofData(filePath)
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")
        super.onDestroy()
    }

    protected fun finalize() {
        // finalization logic
        Log.d(tag, "finalize")
    }
}