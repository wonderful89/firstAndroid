package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import javax.inject.Inject
import javax.inject.Named

class MockActivity6 {
    @Inject
    lateinit var shoe4: Shoe4
//    @Inject @Named("55")
//    lateinit var shoe5: BaseClass

    init {
        DaggerInjectComponent6.builder().build().inject(this)
    }

    fun description() {
        shoe4.description()
//        shoe5.description()
    }
}

open class BaseClass{}

class Shoe4 @Inject constructor() : BaseClass() {
    fun description() {
        print("in shoe44 ")
    }
}

/**
 * 错误: @Qualifier annotations are not allowed on @Inject constructors.
 */
//class Shoe5 @Inject @Named("55") constructor() : BaseClass() {
//    fun description() {
//        print("in shoe55 ")
//    }
//}

@Component
interface InjectComponent6 {
    fun inject(mainClass: MockActivity6)
}

fun main() {
    val activity = MockActivity6()
    activity.description()
}