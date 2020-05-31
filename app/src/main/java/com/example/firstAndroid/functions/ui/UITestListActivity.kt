package com.example.firstAndroid.functions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.TestListActivity
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.functions.ui.listview.TestListViewActivity
import com.example.firstAndroid.functions.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_test_list.*

class UITestListActivity : BaseActivity() {

    companion object{
        const val tag = "UITestList"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)
//        title = this.javaClass.simpleName //"UI测试"

        val lists = UIMainTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2
        listView.setSelector(R.drawable.listview_selector_0)

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentActivity: AppCompatActivity? = null
            intentActivity = when (position) {
                UIMainTest.ListView.ordinal -> TestListViewActivity()
                UIMainTest.Login.ordinal -> LoginActivity()
                else -> MainActivity()
            }

            val intent = android.content.Intent(this, intentActivity.javaClass)
            startActivity(intent)
        }
    }
}