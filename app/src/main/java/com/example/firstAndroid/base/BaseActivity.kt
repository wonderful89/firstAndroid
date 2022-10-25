package com.example.firstAndroid.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.example.firstAndroid.functions.logic.purefunction.WeakRef
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.resource.watcher.ActivityRefWatcher
import com.tencent.matrix.util.MatrixLog
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
    var reference: WeakReference<Any>? = null
    private var queueLooper = false

    fun startLoop() {
        if (!queueLooper) {
            queueLooper = true
            iteratorCall()
        }
    }

    private fun triggerGc() {
        val current = System.currentTimeMillis()
        MatrixLog.v("TAG", "triggering gc...")
        Runtime.getRuntime().gc()
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
//            LogUtils.printErrStackTrace(ActivityRefWatcher.TAG, e, "")
        }
        Runtime.getRuntime().runFinalization()
        LogUtils.v("TAG", "gc was triggered.")
    }

    fun testRef1(obj: Any) {
        reference = WeakReference(obj)
        println("111 ${reference!!.get()}")
        WeakRef.myGc()
        triggerGc()

        Thread.sleep(3000)
        println("111 222 ${reference!!.get()}")
        printLoop2()
    }

    private fun printLoop2() {
        Handler(Looper.getMainLooper()).postDelayed({
            println("111 333 ${reference!!.get()}")
            printLoop2()
        }, 2000)
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

            ReferenceObjWrap.testRef1(this)
        }

//        ReferenceObjWrap.testRef1(this)
    }


    open fun onCreating(savedInstanceState: Bundle?){}
}


