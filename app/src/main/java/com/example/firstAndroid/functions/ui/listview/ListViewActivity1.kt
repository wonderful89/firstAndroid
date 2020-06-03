package com.example.firstAndroid.functions.ui.listview

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.firstAndroid.R
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list_view_1.*
import kotlinx.android.synthetic.main.activity_test_list.listView


class ListViewActivity1 : AppCompatActivity() {
    companion object{
        const val tag = "ListViewActivity1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_1)
        title = "ListViewActivity1"
//        configSimple2()
//        configSingleChoose()
//        configMultiChoose()
//        configCheck()
        configCustomItem()
    }

    private fun initData() : ArrayList<HashMap<String, Any>>{
        var dataItems = ArrayList<HashMap<String, Any>>()
        for (i in 0..19) {
            val map = HashMap<String, Any>()
            map["image"] = R.mipmap.ic_launcher //图标
            map["title"] = "标题$i" //标题
            map["content"] = "内容$i" //内容
            dataItems.add(map)
        }
        return dataItems
    }

    ///
    private fun configCustomItem() {
        val arrayList = initData()
        val adapter = CustomAdapter(this,arrayList)
        listView.adapter = adapter
    }

    /// 配置check
    private fun configCheck() {
        val data = arrayOf(
            "老师",
            "学生",
            "课桌",
            "体育"
        )
        var originId = android.R.layout.simple_list_item_checked
        val adapter = ArrayAdapter(this, R.layout.item_simple_list_6_checked, data)
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE;
        listView.itemsCanFocus = true
        listView.adapter = adapter
        listView.setItemChecked(2, true)
        btn.setOnClickListener {
            Log.w(tag,"button click")
        }

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            Log.w(tag, "position  = $position")
            Log.w(tag, "list view selected  = ${listView.checkedItemPositions}")
        }
    }

    /// 配置多选
    private fun configMultiChoose() {
        val data = arrayOf(
            "老师",
            "学生",
            "课桌",
            "体育"
        )
        var originId = android.R.layout.simple_list_item_multiple_choice
        val adapter = ArrayAdapter(this, R.layout.item_simple_list_5_multi_choose, data)
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE;
        listView.itemsCanFocus = true
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            Log.w(tag, "position  = $position")
            Log.w(tag, "list view selected  = ${listView.checkedItemPositions}")
        }
    }

    /// 配置单选
    private fun configSingleChoose() {
        val data = arrayOf(
            "老师",
            "学生",
            "课桌"
        )
        var originId = android.R.layout.simple_list_item_single_choice
        val adapter = ArrayAdapter(this, R.layout.item_simple_list_4_single_choose, data)
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE;
        listView.itemsCanFocus = true
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            Log.w(tag, "position  = $position")
            Log.w(tag, "list view selected  = ${listView.checkedItemPosition}")
        }
    }

    /// 3个textView的情况
    private fun configSimple3() {
        val data = arrayOf(
            mapOf("title" to "title1", "desc" to "this is the desc1", "desc2" to "this is the desc1xxx"),
            mapOf("title" to "title2", "desc" to "this is the desc2", "desc2" to "this is the desc1yyy"),
            mapOf("title" to "title3", "desc" to "this is the desc3", "desc2" to "this is the desc1zzz")
        )
        val adapter = SimpleAdapter(
            this,
            data.toMutableList(),
            R.layout.item_simple_list_2,
            arrayOf("title", "desc", "desc2"),
            intArrayOf(android.R.id.title, android.R.id.content, R.id.textViewContent1)
        )
        listView.adapter = adapter
    }

    /// 两个textView的情况
    private fun configSimple2() {
        val data = arrayOf(
            mapOf("title" to "title1 are you 1 are you 1 are you 1 are you 1 are you 1 are you", "desc" to "this is the desc1"),
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