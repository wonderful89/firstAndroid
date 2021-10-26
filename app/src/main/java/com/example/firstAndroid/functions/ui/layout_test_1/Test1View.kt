package com.example.firstAndroid.functions.ui.layout_test_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstAndroid.R

/**
 * TODO: document your custom view class.
 */
class Test1View @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    init {
//        context.inflate(R.layout.custom_test_view_in_test_activity, this, true)
        LayoutInflater.from(context).inflate(R.layout.custom_test_view_in_test_activity, this, true)
    }
}