package com.example.firstAndroid.functions.logic.reflect

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.databinding.ActivityLogicReflectBinding
import java.lang.reflect.Field
import java.lang.reflect.Method

@Route(path = "/logic/reflect")
class LogicReflectActivity : BaseActivity() {
    private  lateinit var binding: ActivityLogicReflectBinding
    private val testReflectObj = TestReflectClass()
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogicReflectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            Log.d(tag, "button1 click")
        }

        binding.button2.setOnClickListener {
            Log.d(tag, "button2 click")
            hookOnClickListener(binding.button1)
        }
        binding.button3.setOnClickListener {
            Log.d(tag, "button3 click")
            testReflectObj.ddd()
            Log.d(tag, "Build.ID = ${Build.ID}")
            Log.d(tag, "Build.BOARD = ${Build.BOARD}")
            Log.d(tag, "Build.MODEL = ${Build.MODEL}")
            Log.d(tag, "Build.SERIAL = ${Build.SERIAL}")
            Log.d(tag, "Build.USER = ${Build.USER}")
            Log.d(tag, "Build.HOST = ${Build.HOST}")
            Log.d(tag, "button3 click end")
        }

        binding.button4.setOnClickListener {
            Log.d(tag, "button4 click")
            val dddMethod = TestReflectClass::class.java.getDeclaredMethod("ddd")
//            val dddMethodHook = TestReflectClass::class.java.getDeclaredMethod("dddHook")
//            val dddMethodHook = TestReflectClass::class.java.getMethod("dddHook");
            dddMethod.isAccessible = true

            dddMethod.invoke(testReflectObj)
        }
    }
}

/**
 * [https://www.jianshu.com/p/4f6d20076922](https://www.jianshu.com/p/4f6d20076922)
 */
@SuppressLint("DiscouragedPrivateApi", "PrivateApi")
private fun hookOnClickListener(view: View) {
    try {
        // 得到 View 的 ListenerInfo 对象
        val getListenerInfo = View::class.java.getDeclaredMethod("getListenerInfo")
        getListenerInfo.isAccessible = true
        val listenerInfo = getListenerInfo.invoke(view)
        // 得到 原始的 OnClickListener 对象
        val listenerInfoClz = Class.forName("android.view.View\$ListenerInfo")
        val mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener")
        mOnClickListener.isAccessible = true
        val originOnClickListener = mOnClickListener[listenerInfo] as View.OnClickListener
        // 用自定义的 OnClickListener 替换原始的 OnClickListener
        val hookedOnClickListener: View.OnClickListener =
            HookedOnClickListener(originOnClickListener)
        mOnClickListener[listenerInfo] = hookedOnClickListener
    } catch (e: Exception) {
        Log.e("hookOnClickListener", "hook clickListener failed!", e)
    }
}

internal class HookedOnClickListener(private val origin: View.OnClickListener?) :
    View.OnClickListener {
    override fun onClick(v: View) {
//        Toast.makeText(this@LogicReflectActivity, "hook click", Toast.LENGTH_SHORT).show()
        Log.i("HookedOnClickListener" , "button1 Before click, do what you want to to.")
        origin?.onClick(v)
        Log.i("HookedOnClickListener","button1 After click, do what you want to to.")
    }
}

class TestReflectClass{
    fun ddd() {
        Log.i("TestReflectClass", "in dddd")
    }
}

fun TestReflectClass.dddHook(){
    Log.i("TestReflectClass", "before in dddd")
    this.ddd()
    Log.i("TestReflectClass", "after in dddd")
}