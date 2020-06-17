package com.example.firstAndroid.functions.ui.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import org.jetbrains.anko.*

class Anko2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Anko2ActivityUI().setContentView(this)
    }
}

class Anko2ActivityUI : AnkoComponent<Anko2Activity> {
//    override fun createView(ui: AnkoContext<Anko2Activity>): View = with(ui){
//        verticalLayout {
//            val name = editText {}
//            val myNameShow = editText {
//                isFocusable = false
//            }
//            button("say hello2") {
//                setOnClickListener {
//                    toast("hello, ${name.text}")
//                    myNameShow.text = name.text
//                }
//            }
//        }
//    }

    override fun createView(ui: AnkoContext<Anko2Activity>): View = ui.apply{
        verticalLayout {
            val name = editText {}.lparams(width = dip(200), height = dip(60))
            val myNameShow = editText {
                isFocusable = false
            }
            button("say hello2") {
                setOnClickListener {
                    toast("hello, ${name.text}")
                    myNameShow.text = name.text
                }
            }
        }
    }.view
}