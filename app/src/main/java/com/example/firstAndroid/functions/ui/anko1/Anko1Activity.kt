package com.example.firstAndroid.functions.ui.anko1

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import java.util.logging.Logger

class MyView(
    context: Context
    , private val backColor: Int = Color.BLACK
) : View(context) {
    init {
        backgroundColor = backColor
    }
}

//fun ViewManager.myVView(): MyView = myVView() {}
inline fun ViewManager.myVView(init: (@AnkoViewDslMarker MyView).() -> Unit): MyView {
    return ankoView({ MyView(context = it, backColor = Color.RED) }, theme = 0) { init() }
}

class Anko1Activity : AppCompatActivity() {

    companion object {
        val log: Logger = Logger.getLogger(this::class.java.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            val name = editText {
                setOnFocusChangeListener { v, hasFocus -> log.info("v = $v, hasFocus = $hasFocus") }
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        log.info("afterText")
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        log.info("beforeText")
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        log.info("textChanged: $s")
                    }
                })
            }
            val myNameShow = editText {
                isFocusable = false
            }
            button("say hello") {
                setOnClickListener {
                    toast("hello, ${name.text}")
                    myNameShow.text = name.text
                }
            }

            linearLayout {
                button("button1") {
                    setOnClickListener {
                        toast("hello, button1")
                    }
                }
                button("button2") {
                    setOnClickListener {
                        toast("hello, button2")
                    }
                }

                val myView = myVView() {
                    layoutParams = ViewGroup.LayoutParams(200, 100)
                    leftPadding = 100
                }
                lparams(width = matchParent, height = 200)
                backgroundColor = Color.GRAY
                gravity = Gravity.CENTER_VERTICAL
//                gravity = Gravity.FILL_HORIZONTAL
            }
        }
    }
}