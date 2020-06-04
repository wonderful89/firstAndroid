package com.example.firstAndroid.functions.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Typeface

/**
 * TODO: document your custom view class.
 * @JvmOverloads
 * construction
 */
class DotView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    dots: Dots
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        color = Color.RED
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val dotsPrivate = dots
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val dots = dotsPrivate.getDots().toList()
        for(dot in dots) {
            paint.color = dot.color
            canvas?.drawCircle(dot.x, dot.y, dot.diameter.toFloat(), paint)
        }
    }
}