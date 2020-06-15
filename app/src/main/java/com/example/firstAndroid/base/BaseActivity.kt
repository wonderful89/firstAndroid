package com.example.firstAndroid.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.logging.Logger

open class BaseActivity : AppCompatActivity() {
    companion object{
        const val tag = "BaseActivity"
        val log: Logger = Logger.getLogger(this::class.java.name)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = this.javaClass.simpleName
    }
}