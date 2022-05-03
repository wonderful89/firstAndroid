package com.example.firstAndroid.functions.ui.layout_test_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.firstAndroid.databinding.ActivityLayoutTest1Binding

//import okhttp3.Request
//

class LayoutTest1Activity : AppCompatActivity() {

    companion object {
        const val tag = "LayoutTest1Activity"
    }

    private lateinit var binding: ActivityLayoutTest1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_layout_test1)
        Log.w("LayoutTest1Activity", "LayoutTest1Activity onCreate")

        binding = ActivityLayoutTest1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.test1View.onViewListener = object : OnTest1ViewListener {
            override fun onLookDemo() {
                Log.w(tag, "onLookDemo")
            }

            override fun otherClick(view: View?) {
                TODO("Not yet implemented")
            }

        }

        binding.textview2.setOnClickListener {
            Log.w("LayoutTest1Activity", "textview2 demo button clicked")
        }
    }
}