package com.example.firstAndroid.functions.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.firstAndroid.R
import com.example.firstAndroid.base.utils.inflate

class Custom1View(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
}


class Custom2View @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        context.inflate(R.layout.view_custom_test_1, this, true)
    }
}

// 两个参数目前不会 crash
class CustomCrashView(
    context: Context,
    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, 0) {
    init {
        context.inflate(R.layout.view_custom_test_1, this, true)
    }
}

class Custom3View : LinearLayout {
    constructor(context: Context) : super(context) {
        Log.d("Custom3View", "111")
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.d("Custom3View", "222")
    }
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.d("Custom3View", "333")
    }

    init {
        context.inflate(R.layout.view_custom_test_1, this, true)
    }
}