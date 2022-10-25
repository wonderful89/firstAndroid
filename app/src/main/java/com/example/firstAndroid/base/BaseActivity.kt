package com.example.firstAndroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
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

    override fun onDestroy() {
        LogUtils.dTag(tag, "onDestroy")
        super.onDestroy()

        /**
         * 这里没有释放，原因？
         */
        val testRef = false
        if (testRef) {
            ReferenceObjWrap.startLoop()
//            ReferenceObjWrap.enqueue(this)

            ReferenceObjWrap.testRef1(this)
//            ReferenceObjWrap.testRef22(this)
        }
    }


    open fun onCreating(savedInstanceState: Bundle?){}
}


