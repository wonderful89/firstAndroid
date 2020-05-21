package com.example.firstandroid.functions.test.listview

import android.R
import android.app.ListActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.simple_list_item_x1.view.*


class ListActivityOne : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = arrayOf(
            "老师",
            "学生",
            "课桌",
            "书本",
            "铅笔",
            "橡皮",
            "粉笔",
            "黑板",
            "凳子",
            "扫帚",
            "簸箕",
            "炉子",
            "窗花",
            "讲台",
            "教鞭",
            "小红花",
            "花瓶"
        )
        val arrayAdapter =
            ArrayAdapter(this, R.layout.simple_list_item_1, data)
        listAdapter = arrayAdapter

        listView.dividerHeight = 8
        ///
//        listView?.text1?.gravity = Gravity.CENTER
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Toast.makeText(this, "点中了第${position}个", Toast.LENGTH_SHORT).show()
    }

}
