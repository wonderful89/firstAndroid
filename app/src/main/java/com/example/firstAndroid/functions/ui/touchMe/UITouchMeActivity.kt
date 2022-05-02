package com.example.firstAndroid.functions.ui.touchMe

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityUITouchMeBinding
import kotlin.random.Random

@ExperimentalUnsignedTypes
class UITouchMeActivity : BaseActivity() {
    private var dotsModel = Dots()
    private lateinit var binding: ActivityUITouchMeBinding
    private val mDotView: DotView by lazy {
        val view = DotView(
            this,
            dots = dotsModel
        )
        view.setBackgroundColor(Color.BLUE)
        return@lazy view;
    }

    val dotsChangeHandler = object : Dots.DotsChangeListener {
        override fun onDotsChange(dots: Dots) {
            mDotView.invalidate()
        }
    }

//    override fun onDotsChange(dots: Dots) {
//        mDotView.invalidate()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_u_i_touch_me)
        binding = ActivityUITouchMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dotsModel.addDot(10.toFloat(), 10.toFloat(), Color.RED, 10)

        // 第1种设置方法
//        dotsModel.setDotsChangeListener(dotsChangeHandler)
        // 第2种设置方法
//        dotsModel.setDotsChangeListener2 {
//            mDotView.invalidate()
//        }
        // 第3种设置方法
//        dotsModel.setDotsChangeListener(this)

//        // 第4种设置方法
//        dotsModel.setDotsChangeListener(object: Dots.DotsChangeListener{
//            override fun onDotsChange(dots: Dots) {
//                mDotView.invalidate()
//            }
//        })

        // 第5种设置方法
//        dotsModel.onDotsChanged {
//            mDotView.invalidate()
//        }

        // 第6种设置方法
        dotsModel.setListeners {
            onDotsChangeXX {
                mDotView.invalidate()
            }
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
//        val width = container.width;
        val height = 700;
        val containerParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            height, // ViewGroup.LayoutParams.WRAP_CONTENT
            0.0F
        )
        mDotView.layoutParams = containerParams
        binding.container.addView(mDotView)

        binding.button.setOnClickListener {
            try {
                val c = Random.nextInt(0, 0).toFloat()
            } catch (e: Exception) {
                Log.w("exception", "$e")
            }

            val x = Random.nextInt(0, width).toFloat()
            val y = Random.nextInt(0, height).toFloat()
            dotsModel.addDot(x, y, Color.RED, 10)
        }

        binding.button2.setOnClickListener {
            val x = Random.nextInt(0, width).toFloat()
            val y = Random.nextInt(0, height).toFloat()
            dotsModel.addDot(x, y, Color.WHITE, 10)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        Log.w("UITouchMeActivity", "onDestroy")
        super.onDestroy()
    }
}