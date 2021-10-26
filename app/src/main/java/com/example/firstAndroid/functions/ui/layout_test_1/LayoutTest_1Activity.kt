package com.example.firstAndroid.functions.ui.layout_test_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_layout_test1.*
import com.example.firstAndroid.base.utils.*
import kotlinx.android.synthetic.main.fragment_test1_in_test_activity.*

class LayoutTest1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_test1)
        Log.w("LayoutTest1Activity", "LayoutTest1Activity onCreate")



//        test2_fragment.look_demo.setOnClickListener {
//            Log.w("LayoutTest1Activity","look demo button clicked")
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.test2_fragment, Test2Fragment())
////            transaction.show(test2_fragment)
//            transaction.commit()
//        }

//        look_demo.setOnClickListener {
//            Log.w("LayoutTest1Activity","look demo button clicked")
//            Log.w("","look demo button clickedXXX")
//        }

        textview2.setOnClickListener {
            Log.w("LayoutTest1Activity", "textview2 demo button clicked")
        }
    }
}