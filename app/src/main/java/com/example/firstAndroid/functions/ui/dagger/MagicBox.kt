package com.example.firstAndroid.functions.ui.dagger

import android.util.Log
import dagger.Component
import kotlinx.android.synthetic.main.item_simple_list_6_checked.view.*
import javax.inject.Inject

class  MyMagicClass constructor(){

    @Inject lateinit var info: Info

    init {
        DaggerMagicBox.create().poke(this)
    }
}

class  MyMagicClass2 constructor(){

    @Inject lateinit var info: Info
    @Inject lateinit var info2: Info2

    init {
//        DaggerMagicBox.create().myPoke2(this)
    }
}

class MyMagicClass3 constructor()

/**
 * 没有@Inject
 *
 * 错误: [Dagger/MissingBinding] com.example.firstAndroid.functions.ui.dagger.Info cannot be provided without an @Inject constructor or an @Provides-annotated method.
 */
class Info @Inject constructor() {
    fun test(){
        println("in test...")
    }
}

class Info2 @Inject constructor()


@Component interface MagicBox {
    var info: Info
//    val info2: Info2
    fun poke(mainClass: MyMagicClass)

    fun poke2(name: String)

    fun poke3(mainClass3: MyMagicClass3)

    /**
     * 错误: This method isn't a valid provision method, members injection method or subcomponent factory method. Dagger cannot implement this method
    public abstract void poke2(@org.jetbrains.annotations.NotNull()
     */
//    fun poke2(mainClass: MyMagicClass, name: String)
//    fun myPoke2(mainClass: MyMagicClass2)
}

fun main(){
    println("main begin2")
    val info = DaggerMagicBox.create().info
    info.test()
    println("info = $info")
    val info2 = MyMagicClass().info
    println("info2 = $info2")
    println("main end")
}