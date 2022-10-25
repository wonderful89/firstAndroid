package com.example.firstAndroid.base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.LogUtils
import com.example.firstAndroid.functions.logic.download.downloader.Observer
import com.example.firstAndroid.functions.logic.purefunction.WeakRef
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*
import java.util.logging.Logger
import javax.inject.Inject


object ReferenceObjWrap {
    var queue: ReferenceQueue<Any> = ReferenceQueue()
    var weakHashMap = WeakHashMap<Activity, String>()
    private var queueLooper = false

    fun startLoop() {
        if (!queueLooper) {
            queueLooper = true
            iteratorCall()
        }
    }

    private fun printlnQueue(tag: String) {
        WeakRef.myGc()
        println(tag)
        var obj: Any? = queue.poll()
        // 循环打印引用队列
        while (obj != null) {
            println("===: $obj")
            obj = queue.poll()
        }
        println("end===")
    }

    fun enqueue(obj: Any) {
        val reference: WeakReference<Any> = WeakReference(obj, queue)
////        queue.enqueue(reference)
////        queue.poll()
        println("reference = $reference")
//        printlnQueue("reference first==")

//        weakHashMap.put(obj as Activity, "1111")
    }

    private fun iteratorCall() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            printlnQueue("reference after")
            iteratorCall()
        }, 4000)
    }
}




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
            ReferenceObjWrap.enqueue(this)
        }
    }


    open fun onCreating(savedInstanceState: Bundle?){}
}


