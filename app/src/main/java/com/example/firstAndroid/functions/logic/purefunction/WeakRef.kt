package com.example.firstAndroid.functions.logic.purefunction

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.example.firstAndroid.TestListActivity
import com.example.firstAndroid.functions.logic.LogicListActivity
import java.lang.Exception
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*

object WeakRef {

    fun myGc() {
        Runtime.getRuntime().gc()
        Thread.sleep(200)
    }

    fun test() {
//        testWeakRef()
//        testWeakRef2()
        JavaDemo.mainDDD()
//        testWeakRef3()
    }

    private fun testWeakRef() {
        // 创建一个对象
        var obj: Any? = Any()
        // 创建一个弱引用，并指向这个对象，并且将引用队列传递给弱引用
        val reference: WeakReference<Any> = WeakReference(obj)
        // 打印出这个弱引用，为了跟gc之后queue里面的对比证明是同一
        Log.d(LogicListActivity.tag, "这个弱引用是:$reference")
        // gc一次看看(毛用都没)
        myGc()
        Log.d(LogicListActivity.tag, "before 这个弱引用是==:${reference.get()}")
        // 先设置obj为null，obj可以被回收了
        obj = null
        // 再进行gc，此时obj应该被回收了，那么queue里面应该有这个弱引用了
        myGc() // System.gc()
        Log.d(LogicListActivity.tag, "after 这个弱引用是==:${reference.get()}")
    }

    private fun testWeakRef3() {
        //创建一个引用队列
        val queue = ReferenceQueue<Any>()
        // 创建弱引用，此时状态为Active，并且Reference.pending为空，
        // 当前Reference.queue = 上面创建的queue，并且next=null
        // reference 创建并关联 queue
        try {
            val reference: WeakReference<*> = WeakReference(Any(), queue)
            LogUtils.dTag(LogicListActivity.tag, "reference = $reference")
            // 当GC执行后，由于是弱引用，所以回收该object对象，并且置于pending上，此时reference的状态为PENDING
            myGc() // System.gc()
            // ReferenceHandler从 pending 中取下该元素，并且将该元素放入到queue中，
            //此时Reference状态为ENQUEUED，Reference.queue = ReferenceENQUEUED

            // 当从queue里面取出该元素，则变为INACTIVE，Reference.queue = Reference.NULL
            /// 会卡在这里
            val reference1 = queue.poll()
            LogUtils.dTag(LogicListActivity.tag, "reference1 = $reference1")
        } catch (e: Exception) {
            LogUtils.eTag(LogicListActivity.tag, "excep = $e")
        }
    }

    private fun testWeakRef2() {
        val cache = WeakHashMap<TestListActivity, String>()
        for (i in 0..20000) {
            cache.put(TestListActivity(), "cache$i")
        }
        Log.d(LogicListActivity.tag, "cache.size = ${cache.size}")//675697

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(LogicListActivity.tag, "cache.size22 = ${cache.size}")//675697
        }, 3000)

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(LogicListActivity.tag, "cache.size33 = ${cache.size}")//675697
        }, 10000)
    }
}