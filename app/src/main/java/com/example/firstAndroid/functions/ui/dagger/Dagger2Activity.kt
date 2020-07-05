package com.example.firstAndroid.functions.ui.dagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import java.lang.reflect.Constructor
import javax.inject.Inject

class Dagger2Activity @Inject constructor(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

class Dagger2ActivityMock @Inject constructor() {
    @Inject lateinit var pot: Pot; // 属性注入
    private lateinit var potMethod:Pot // 方法注入

    @Inject fun setPot2(pot: Pot){
        potMethod = pot
    }


    fun test(){
        println("in test")
        DaggerDagger2ActivityModule.create().inject(this);
        val show = pot.show()
        println("show = $show");
    }

    fun test2(){
        println("in test2")
        DaggerDagger2ActivityModule.create().inject(this);
        val show = potMethod.show()
        println("show = $show");
    }
}

@Component interface MagicBoxXX {
    var rose: Rose;
}

@Component interface Dagger2ActivityModule { //
    public fun inject(activity: Dagger2ActivityMock);
}

class Pot @Inject constructor(rose:Rose) {
    @Inject lateinit var  rose:Rose;
    fun  show() : String{
        return rose.whisper();
    }
}

class Rose @Inject constructor() {
    fun whisper(): String {
        return "热恋";
    }
}

fun main(){
    println("dagger2 begin")
    val activity = Dagger2ActivityMock()
    println("activity = $activity")
//    activity.test()
    activity.test2()
    println("dagger2 end")
}

/// 依赖关系
// Dagger2ActivityMock -> Pot -> Rose