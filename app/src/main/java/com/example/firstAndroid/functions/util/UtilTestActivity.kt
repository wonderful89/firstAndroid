package com.example.firstAndroid.functions.util

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.base.utils.FileConstants
import com.example.firstAndroid.databinding.ActivityTestListBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

//import kotlinx.android.synthetic.main.activity_test_list.*

class UtilTestActivity : BaseActivity() {
    companion object{
        const val tag = "UtilTestList"
    }

    private lateinit var binding: ActivityTestListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Permission.checkPermission(this)

        val lists = UtilTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
//            var intentClass: Class<AppCompatActivity>? = null
//            intentClass = when (position) {
//                else -> MainActivity().javaClass
//            }
//
//            val intent = android.content.Intent(this, intentClass)
//            startActivity(intent)
            when(lists[position]) {
                "相机测试" -> {
                    val intent = Intent(this, CameraTestActivity::class.java)
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }
}