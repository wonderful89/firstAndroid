package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import javax.inject.Inject

class MockActivity3 {
    /**
     * step: 3: 定义依赖的类 MockActivity2，有一个外部的依赖 Shoe2 和 Shoe3
     */
    @Inject
    lateinit var shoe2: Shoe2
    @Inject
    lateinit var shoe3: Shoe3

    init {
        /**
         * step: 4: 实现注入的方法，加 Dagger 前缀
         */
//        DaggerInjectComponent3.create().inject(this)
        DaggerInjectComponent3.builder().build().inject(this)
    }

    fun description() {
        shoe2.description()
        shoe3.description()
    }
}

/**
 * step: 1: 定义被依赖的类，可以注入
 */
class Shoe3 @Inject constructor() {
    fun description() {
        print("in shoe3")
    }
}

/**
 * step: 2: 为依赖的类 MockActivity2 定义链接
 */
@Component
interface InjectComponent3 {
    fun inject(mainClass: MockActivity3)
}

fun main() {
    val activity = MockActivity3()
    activity.description()
}