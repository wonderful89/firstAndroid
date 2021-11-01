package com.example.firstAndroid.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.logging.Logger
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {
    companion object{
        const val tag = "BaseActivity"
        val log: Logger = Logger.getLogger(this::class.java.name)
    }

    open fun isDagger(): Boolean = false


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isDagger()) {
            AndroidInjection.inject(this)
        }
        onCreating(savedInstanceState)
//        val title2 = intent.getStringExtra("title")
        title = this.javaClass.simpleName
    }


    open fun onCreating(savedInstanceState: Bundle?){}
}