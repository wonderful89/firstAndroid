package com.example.firstAndroid.functions.ui.dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firstAndroid.R
import dagger.Component
import javax.inject.Inject

/**
 * 这是一个注解类型，哈哈哈
 * 可以包含特殊语法
 */

class Dagger1Activity : AppCompatActivity() {
    lateinit var model: Dagger1Model

    companion object {
        const val tag = "Dagger1Activity"
    }

    /**
     * step: 4
     */
    init {
        DaggerInjectComponent.create().poke(this)
    }

    /**
     * step: 3
     */
    @Inject
    lateinit var shoe2: Shoe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger1)
//        testMagicBox()
//        testMyMagicBox()

        val show = Shoe()
        show.description()

        Log.w(tag, "shoe2 = $shoe2")
        shoe2.description()
    }

    private fun testMagicBox() {
        val box = DaggerMagicBox.create()
        val info = box.info
        val info2 = box.info
        info.test()
        Log.w("Dagger", "info = $info, info2 = $info2")

    }

    private fun testMyMagicBox() {
        val magicObj = MyMagicClass()
        Log.w("Dagger", "magicObj.info = ${magicObj.info}")
    }
}

/**
 * step: 1
 */
class Shoe @Inject constructor() {
    fun description() {
        Log.w(Dagger1Activity.tag, "in shoes")
    }
}

/**
 * step: 2
 */
@Component
interface InjectComponent {
    var show: Shoe
    fun poke(mainClass: Dagger1Activity)
}