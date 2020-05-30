package com.example.firstAndroid.functions.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_test_list.*

class ComponentActivity : AppCompatActivity() {
    companion object{
        const val tag = "UITestList"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        val lists = ComponentTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        listView.adapter = arrayAdapter2
        listView.dividerHeight = 2
        listView.setSelector(R.drawable.listview_selector_0)

        listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentClass: Class<AppCompatActivity>? = null
            intentClass = when (position) {
                else -> MainActivity().javaClass
            }

            val intent = android.content.Intent(this, intentClass)
            startActivity(intent)
        }
    }
}