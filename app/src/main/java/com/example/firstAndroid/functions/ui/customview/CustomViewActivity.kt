package com.example.firstAndroid.functions.ui.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firstAndroid.R
import com.example.firstAndroid.base.utils.getColor2
import com.example.firstAndroid.base.utils.getDimensionPixelSize
import com.example.firstAndroid.base.utils.showToast
import kotlinx.android.synthetic.main.activity_custom_view.*
import kotlinx.android.synthetic.main.view_custom_test_1.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.contentView

class CustomViewActivity : AppCompatActivity() {
    companion object {
        private const val tag = "CustomViewActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        test1()
        test2()
    }

    private fun test2() {
        setContentView(R.layout.activity_custom_view)
//        val custom2 = Custom2View(baseContext)
//        content_view.addView(custom2)

//        custom_view_2.setOnClickListener {
//            Log.i(tag, "custom_view_2 clicked")
//        }
//        custom_view_2.tvContent.setOnClickListener {
//            Log.i(tag, "tvContent clicked")
//        }
    }

    private fun test1() {
        val custom1 = Custom1View(this.baseContext)
        setContentView(custom1)
        custom1.layoutParams.width = getDimensionPixelSize(R.dimen.dp180)
        custom1.layoutParams.height = getDimensionPixelSize(R.dimen.dp180)
        custom1.backgroundColor = getColor2(R.color.colorBlackOp4)

        custom1.setOnClickListener {
            Log.i(tag, "custom1 clicked")
            "custom1 clicked".showToast()
        }
    }
}