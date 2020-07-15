package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import javax.inject.Inject

class Pot @Inject constructor() {
    @Inject
    lateinit var rose: Rose;

    @Inject
    lateinit var rose2: Rose2;
    fun show(): String {
        return rose.whisper() + rose2.whisper();
    }

    fun hide(): String {
        return rose2.whisper();
    }
}

class Pot2 @Inject constructor(_rose: Rose) {
    private var rose: Rose = _rose;
    fun show(): String {
        return "Pot2 ~~" + rose.whisper();
    }
}

class Rose @Inject constructor() {
    fun whisper(): String {
        return "hello, whisper ";
    }
}

class Rose2 @Inject constructor() {
    fun whisper(): String {
        return "hello, whisper222 ";
    }
}

class Dagger2ActivityMock @Inject constructor() {
    @Inject
    lateinit var pot: Pot; // 属性注入
    private lateinit var potMethod: Pot // 方法注入

    @Inject
    lateinit var pot22: Pot2; // 属性注入

    @Inject
    fun setPot2(pot: Pot) {
        potMethod = pot
    }

    fun test() {
        println("in test: 属性注入")
        DaggerActivityComponent.create().inject(this);
        val show = pot.show()
        println("pot22 = ${pot22.show()}");
        println("show = $show");
    }

    fun test2() {
        println("in test2: 方法注入")
        DaggerActivityComponent.create().inject(this);
        val show = potMethod.show()
        println("show = $show");
    }
}

@Component
interface ActivityComponent { //
    public fun inject(activity: Dagger2ActivityMock);
}

fun main() {
    println("dagger2 begin2")
    val activity = Dagger2ActivityMock()
    println("activity = $activity")
    activity.test()
    activity.test2()
    println("dagger2 end")
}

/// 依赖关系
// Dagger2ActivityMock -> Pot -> Rose
// Dagger2ActivityMock -> Pot : test 为属性注入
// Dagger2ActivityMock -> Pot : test2 为属性注入
// Pot -> Rose: 为属性注入
// Pot2 -> Rose :  为构造方法注入
