package com.example.firstAndroid.functions.ui.layout_test_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstAndroid.R
import com.example.firstAndroid.databinding.CustomTestViewInTestActivityBinding

/**
 * TODO: document your custom view class.
 */

open interface OnTest1ViewListener {
    fun onLookDemo()
    fun otherClick(view: View?)
}

class Test1View @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    open var onViewListener: OnTest1ViewListener? = null
    private lateinit var binding: CustomTestViewInTestActivityBinding

    init {
//        context.inflate(R.layout.custom_test_view_in_test_activity, this, true)
//        LayoutInflater.from(context).inflate(R.layout.custom_test_view_in_test_activity, this, true)
        binding = CustomTestViewInTestActivityBinding.inflate(LayoutInflater.from(context))
        binding.lookDemo.setOnClickListener {
            Log.w("0", "look demo")
            onViewListener?.onLookDemo()
        }
    }

}