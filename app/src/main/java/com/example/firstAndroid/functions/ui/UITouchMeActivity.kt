package com.example.firstAndroid.functions.ui

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_u_i_touch_me.*
import java.util.*
import kotlin.random.Random

class Dot(var x: Float, var y: Float, var color: Int, var diameter: Int) {}

class Dots {
    interface DotsChangeListener {
        fun onDotsChange(dots: Dots);
    }

    private var dots = LinkedList<Dot>()
    private val safeDots = Collections.unmodifiableList(dots)
    private var dotsChangeListener: DotsChangeListener? = null

    fun setDotsChangeListener(listen: DotsChangeListener) {
        dotsChangeListener = listen
    }

    fun getLastDot(): Dot? {
        return dots.last
    }

    fun getDots(): MutableList<Dot> {
        return Collections.unmodifiableList(dots)
    }

    fun addDot(x: Float, y: Float, color: Int, diameter: Int) {
        dots.add(Dot(x, y, color, diameter))
        notifyListener()
    }

    fun clearDots() {
        dots.clear()
        notifyListener()
    }

    private fun notifyListener() {
        dotsChangeListener?.onDotsChange(this)
    }
}

class UITouchMeActivity : AppCompatActivity(), Dots.DotsChangeListener {
    private var dots = Dots()
    private var mDotView: DotView? = null

    override fun onDotsChange(dots: Dots) {
        mDotView?.invalidate()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_i_touch_me)
        dots.addDot(10.toFloat(), 10.toFloat(), Color.RED, 10)
        mDotView = DotView(this, dots = dots)
        val dotView = mDotView!!
        dotView.setBackgroundColor(Color.BLUE)
        dots.setDotsChangeListener(this)

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
//        val layoutParam = ViewGroup.MarginLayoutParams(200,200)
//        layoutParam.marginStart = 0
//        layoutParam.marginEnd = 0
        dotView.layoutParams = containerParams
        container.addView(dotView)

        button.setOnClickListener {
            try {
                val c = Random.nextInt(0, 0).toFloat()
            } catch (e: Exception) {
                Log.w("exception", "$e")
            }
            val x = Random.nextInt(0, width).toFloat()
            val y = Random.nextInt(0, height).toFloat()
            dots.addDot(x, y, Color.RED, 10)
        }

        button2.setOnClickListener {
            val x = Random.nextInt(0, width).toFloat()
            val y = Random.nextInt(0, height).toFloat()
            dots.addDot(x, y, Color.WHITE, 10)
        }
    }
}