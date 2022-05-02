package com.example.firstAndroid.functions.ui.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityTestListBinding

class TestListViewActivity : BaseActivity() {
    companion object {
        const val tag = "ListViewTest"
    }

    private lateinit var binding: ActivityTestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lists = ListViewTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentClass: Class<AppCompatActivity>? = null
            intentClass = when (position) {
                ListViewTest.ListView1.ordinal -> ListViewActivity1().javaClass
                else -> MainActivity().javaClass
            }

            val intent = android.content.Intent(this, intentClass)
            startActivity(intent)
        }
    }
}