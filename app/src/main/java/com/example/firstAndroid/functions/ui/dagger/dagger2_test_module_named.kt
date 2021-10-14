package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

/**
 * https://bloggie.io/@_junrong/dagger-2-for-android-part-iii-the-qualifier-and-named-annotation
 */

class Cat(val name: String)

@Module
class MainModule3 {
    @Named("red")
    @Provides
    fun getCloth(): Cloth {
        val cloth = Cloth()
        cloth.color = "红色"
        return cloth
    }

    @Provides
    @Named("blue")
    fun getCloth2(): Cloth {
        val cloth = Cloth()
        cloth.color = "蓝色"
        return cloth
    }

    @Provides
    @Named("Garfield")
    fun provideGarfield(): Cat = Cat("Garfield")

    @Provides
    @Named("HelloKitty")
    fun provideHelloKitty(): Cat = Cat("Hello Kitty")
}

/**
 * 中间桥接层，需要手动调动注入代码
 */
@Component(modules = [MainModule3::class])
interface MainComponent5 {
    fun inject(mainActivity: MockActivity5?)
}

class MockActivity5 {
    @Inject
    @field:Named("red")
    lateinit var clothRed: Cloth

    @Inject
    @field:Named("blue")
    lateinit var clothBlue: Cloth

    @Inject
    @field:Named("HelloKitty")
    lateinit var helloKittyCat: Cat

    init {
        val build: MainComponent5 = DaggerMainComponent5.builder().mainModule3(MainModule3()).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${helloKittyCat.name}\n")
        print("in mock activity: ${clothRed.color}\n")
        print("in mock activity: ${clothBlue.color}\n")
    }
}

fun main() {
    println("main begin2")
    val activity = MockActivity5()
    activity.description()
}