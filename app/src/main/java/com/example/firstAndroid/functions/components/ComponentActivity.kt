package com.example.firstAndroid.functions.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.firstAndroid.MainActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityTestListBinding
import com.example.firstAndroid.functions.logic.LogicTest

class ComponentActivity : BaseActivity() {
    companion object{
        const val tag = "UITestList"
    }

    private lateinit var binding: ActivityTestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lists = ComponentTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
            var intentClass: Class<AppCompatActivity>? = null
            intentClass = when (position) {
                ComponentTest.WebViewOrigin.ordinal -> WebViewOriginActivity().javaClass
                ComponentTest.WebViewX5.ordinal -> WebViewX5Activity().javaClass
                else -> MainActivity().javaClass
            }

            val intent = android.content.Intent(this, intentClass)
            startActivity(intent)
        }
    }
}