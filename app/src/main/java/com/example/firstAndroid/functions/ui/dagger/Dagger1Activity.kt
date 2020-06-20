package com.example.firstAndroid.functions.ui.dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firstAndroid.R
import dagger.Component
import javax.inject.Inject

class Dagger1Activity : AppCompatActivity() {
    lateinit var model: Dagger1Model
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger1)
        testMagicBox()
        testMyMagicBox()
    }

    private fun testMagicBox(){
        val box = DaggerMagicBox.create()
        val info = box.info
        val info2 = box.info
        info.test()
        Log.w("Dagger", "info = $info, info2 = $info2")

    }

    private fun testMyMagicBox(){
        val magicObj = MyMagicClass()
        Log.w("Dagger", "magicObj.info = ${magicObj.info}")
    }
}