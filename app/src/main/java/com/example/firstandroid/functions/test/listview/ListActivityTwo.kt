package com.example.firstandroid.functions.test.listview

import android.R
import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList


class ListActivityTwo : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val simpleAdapter = SimpleAdapter(
            this,
            this.getData(),
            android.R.layout.simple_list_item_2,
            arrayOf("name", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        listAdapter = simpleAdapter
    }

    fun getData(): ArrayList<Hashtable<String, String>> {
        var retList = ArrayList<Hashtable<String, String>>();
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
        for (index in data.indices) {
            val table = Hashtable<String, String>()
            table["name"] = "第${index}条数据"
            table["desc"] = "desc: ===${data[index]}"
            retList.add(table)
        }
        return retList
    }


    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Toast.makeText(this, "点中了第${position}个", Toast.LENGTH_SHORT).show()
    }

}
