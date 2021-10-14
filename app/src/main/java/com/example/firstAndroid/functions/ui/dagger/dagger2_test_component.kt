package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import javax.inject.Inject

class MockActivity2 {
    /**
     * step: 3: 定义依赖的类 MockActivity2，有一个外部的依赖 Shoe2
     */
    @Inject
    lateinit var shoe2: Shoe2

    init {
        /**
         * step: 4: 实现注入的方法，加 Dagger 前缀
         */
        DaggerInjectComponent2.create().inject(this)
    }

    fun description() {
        shoe2.description()
    }
}

/** step: 1: 定义被依赖的类，可以注入 */
class Shoe2 @Inject constructor() {
    fun description() {
        print("in shoe2")
    }
}

/**
 * step: 2: 为依赖的类 MockActivity2 定义链接
 */
@Component
interface InjectComponent2 {
    fun inject(mainClass: MockActivity2)
}

fun main() {
    val activity = MockActivity2()
    activity.description()
}