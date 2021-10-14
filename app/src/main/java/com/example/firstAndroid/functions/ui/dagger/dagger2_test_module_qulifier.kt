package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

@Module
class MainModule6 {
    @Provides
    @RedCloth
    fun getCloth(): Cloth {
        val cloth = Cloth()
        cloth.color = "红色"
        return cloth
    }

    @Provides
    @BlueCloth
    fun getCloth2(): Cloth {
        val cloth = Cloth()
        cloth.color = "蓝色"
        return cloth
    }
}

/**
 * 中间桥接层，需要手动调动注入代码
 */
@Component(modules = [MainModule6::class])
interface MainComponent6 {
    fun inject(mainActivity: MockActivityQulifier?)
}

class MockActivityQulifier {
    @Inject
    @field:RedCloth
    lateinit var clothRed: Cloth

    @Inject
    @field:BlueCloth
    lateinit var clothBlue: Cloth

    init {
        val build: MainComponent6 =
            DaggerMainComponent6.builder().mainModule6(MainModule6()).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${clothRed.color}\n")
        print("in mock activity: ${clothBlue.color}\n")
    }
}

//import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Qualifier
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class RedCloth

@Qualifier
@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class BlueCloth(
    /** The name.  */
    val value: String = ""
)

fun main() {
    println("main begin2")
    val activity = MockActivityQulifier()
    activity.description()
}