package com.example.firstAndroid.functions.ui.layout_test_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_layout_test1.*
import com.example.firstAndroid.base.utils.*
import kotlinx.android.synthetic.main.fragment_test1_in_test_activity.*
//import okhttp3.Request
//

class LayoutTest1Activity : AppCompatActivity() {

    companion object {
        const val tag = "LayoutTest1Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_test1)
        Log.w("LayoutTest1Activity", "LayoutTest1Activity onCreate")

        test1_view.onViewListener = object : OnTest1ViewListener {
            override fun onLookDemo() {
                Log.w(tag, "onLookDemo")
            }

            override fun otherClick(view: View?) {
                TODO("Not yet implemented")
            }

        }

        textview2.setOnClickListener {
            Log.w("LayoutTest1Activity", "textview2 demo button clicked")
        }
    }
}