package com.example.firstAndroid.functions.ui.layout_test_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import android.graphics.Typeface
import com.example.firstAndroid.R

private enum class FanSpeed(val label: Int) {
    OFF(10),
    LOW(11),
    MEDIUM(12),
    HIGH(13)
}

class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Set dial background color to green if selection not off.
        paint.color = Color.BLACK
        canvas.drawCircle(100F, 100F, 30F, paint)
//        // Draw the text labels.
//        val labelRadius = radius + RADIUS_OFFSET_LABEL
//        for (i in FanSpeed.values()) {
//            pointPosition.computeXYForSpeed(i, labelRadius)
//            val label = resources.getString(i.label)
//            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
//        }
    }

}