package com.example.firstAndroid.functions.ui.listview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_test_list.*

class ListViewActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_1)
        title = "ListViewActivity1"
        configSimple2()
    }

    private fun configSimple2() {
        val data = arrayOf(
            mapOf("title" to "title1 are you dasf fdasjdf jdsafk asdlkfj aslkdf alksdfjalksf aslkfa sfjl", "desc" to "this is the desc1"),
            mapOf("title" to "title2", "desc" to "this is the desc2"),
            mapOf("title" to "title3", "desc" to "this is the desc3")
        )
        val adapter = SimpleAdapter(
            this,
            data.toMutableList(),
//            android.R.layout.simple_list_item_2,
            R.layout.item_simple_list_1,
            arrayOf("title", "desc"),
            intArrayOf(android.R.id.title, android.R.id.content)
        )
        listView.adapter = adapter
    }
    /// 最简单的配置(自己的或者系统的xml)
    private fun configSimple() {
        val data = arrayOf(
            "老师",
            "学生",
            "课桌"
        )
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        val adapter = ArrayAdapter(this, R.layout.item_simple_list_0, data)
        listView.adapter = adapter
    }
}