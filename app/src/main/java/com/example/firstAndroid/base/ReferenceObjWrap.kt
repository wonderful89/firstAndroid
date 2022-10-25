package com.example.firstAndroid.base

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.example.firstAndroid.base.utils.runOnUiThread
import com.example.firstAndroid.functions.logic.purefunction.WeakRef
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*

object ReferenceObjWrap {
    var queue: ReferenceQueue<Any> = ReferenceQueue()
    var weakHashMap = WeakHashMap<Activity, String>()
    var reference: WeakReference<Any>? = null
    var reference2: WeakReference<Any>? = null
    private var queueLooper = false

    fun startLoop() {
        if (!queueLooper) {
            queueLooper = true
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        WeakRef.myGc()
                        printlnQueue("test11 in loop")
                        println("111 333 ${reference?.get()}")
                        println("test11 vv ${reference2?.get()}")
                    }
                }
            }, 0,5000)
        }
    }

    private fun triggerGc() {
        Runtime.getRuntime().gc()
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
//            LogUtils.printErrStackTrace(ActivityRefWatcher.TAG, e, "")
        }
        Runtime.getRuntime().runFinalization()
    }

    fun testRef1(obj: Any) {
        reference = WeakReference(obj)
        println("111 ${reference!!.get()}")
        Thread.sleep(3000)
        println("111 222 ${reference!!.get()}")
    }

    fun testRef22(obj: Any) {
        reference = WeakReference(obj)
        println("111 ${reference!!.get()}")
        WeakRef.myGc()
        triggerGc()

        Thread.sleep(3000)
        println("111 222 ${reference!!.get()}")
    }

    private fun printlnQueue(tag: String) {
        val stringBuf = StringBuffer()
        stringBuf.append("$tag: ")
        var obj: Any? = queue.poll()
        // 循环打印引用队列
        while (obj != null) {
            stringBuf.append("===: $obj")
            obj = queue.poll()
        }
        stringBuf.append("end====")
        println(stringBuf.toString())
    }

    fun enqueue(obj: Any) {
//        val reference: WeakReference<Any> = WeakReference(obj, queue)

        reference2 = WeakReference(obj, queue)
//        reference2 = null
//        queue.enqueue(reference)
//        queue.poll()
        println("test11 reference = $reference2")
    }
}