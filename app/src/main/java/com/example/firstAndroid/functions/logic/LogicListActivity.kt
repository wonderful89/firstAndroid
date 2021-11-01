package com.example.firstAndroid.functions.logic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.functions.ui.UITestListActivity
import com.example.firstAndroid.functions.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_test_list.*

class LogicListActivity : BaseActivity() {
    companion object{
        const val tag = "LogicTestList"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        val lists = LogicTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2
        listView.setSelector(R.drawable.listview_selector_0)

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentName: String? = null
            var pos: LogicTest = LogicTest.values()[position]
//            intentName = when (position) {
//                LogicTest.Network.ordinal -> "/logic/network"
//                LogicTest.Storage.ordinal -> "/logic/storage"
//                else -> "/ui/animation2"
//            }
            intentName = pos.path
            val name = intentName.split("/".toRegex()).lastOrNull() ?: "Unknown"
            ARouter.getInstance().build(intentName)
                .withString("title", name)
                .navigation()
//            val intent = android.content.Intent(this, intentClass)
//            startActivity(intent)
        }
    }
}