package com.example.firstAndroid.base.utils

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking

private val mainHandler = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行
 */
fun runOnUiThread(f: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        f()
    } else {
        mainHandler.post {
            f()
        }
    }

}

/**
 * 主线程延迟执行
 */
fun runOnUiThread(delayMs: Long, f: () -> Unit) {
    mainHandler.postDelayed({
        f()
    }, delayMs)
}

fun runOnBlockingThread(c: CoroutineDispatcher, f: () -> Unit) {
    try {
        runBlocking(c) {
            f.invoke()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}