package com.example.firstAndroid.functions.logic.storage

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
private val FIXED_EXECUTOR = Executors.newFixedThreadPool(4)


/**
 * 一般用于数据库操作
 */
fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

/**
 * 用于处理 数据多的时候的线程
 */
fun runOnFixedThread(f: () -> Unit) {
    FIXED_EXECUTOR.execute(f)
}