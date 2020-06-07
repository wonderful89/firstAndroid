package com.example.firstAndroid.functions.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class UITestView1Activity : AppCompatActivity() {

    companion object {
        val tag = "TestView1"
    }

    // 初始化1
//    private lateinit var rootPrivate: LinearLayout;
    private val rootPrivate by lazy {
        Log.w(tag,"init rootPrivate")
        LinearLayout(this)
    }

    private val aaa: Int by lazy {
        1 + 2;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(tag, "onCreate")
//        setContentView(R.layout.activity_view_test_1)
        val root = rootPrivate
        val containerParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            0.0F
        )

        val widgetParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            1.0F
        )

        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(Color.LTGRAY)
        root.layoutParams = containerParams
        setContentView(root)

        var ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setBackgroundColor(Color.GRAY)
        ll.layoutParams = containerParams
        root.addView(ll)

        val tb = EditText(this)
        tb.setText("X coord")
        tb.isFocusable = true
        tb.layoutParams = widgetParams
        ll.addView(tb)

        val tb2 = EditText(this)
        tb2.setText("Y coord")
        tb2.isFocusable = false
        tb2.layoutParams = widgetParams
        ll.addView(tb2)

        val ll2 = LinearLayout(this)
        ll2.orientation = LinearLayout.HORIZONTAL
        ll2.setBackgroundColor(Color.DKGRAY)
        ll2.layoutParams = containerParams
        root.addView(ll2)

        val button1 = Button(this)
        button1.setText("Red")
        button1.setBackgroundColor(Color.RED)
        button1.layoutParams = widgetParams
        button1.setOnClickListener {
            Log.w(tag, "button click")
            tb.setText(Random.nextInt(200, 1000).toString())
        }
        ll2.addView(button1)

        val button2 = Button(this)
        button2.setText("Green")
        button2.setBackgroundColor(Color.WHITE)
        button2.layoutParams = widgetParams
        ll2.addView(button2)
        button2.setOnClickListener {
            Log.w(tag, "button2 click")
            tb2.setText(Random.nextInt(0, 200).toString())
        }

        val button3 = Button(this)
        button3.setText("YELLOW")
        button3.setBackgroundColor(Color.YELLOW)
        button3.layoutParams = widgetParams
        ll2.addView(button3)

    }

    override fun onDestroy() {
        Log.w(tag, "onDestroy")
        super.onDestroy()
    }
}
