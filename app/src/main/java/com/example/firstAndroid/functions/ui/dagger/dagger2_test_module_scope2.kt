package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
import javax.inject.Singleton

/**
 * 当我们将这个注解使用在Module类中的Provide方法上时,就是声明这个Provide方法是在 CustomSingleton 作用范围内的,并且当一个Component要引用这个Module时,必须也要声明这个Component是 CustomSingleton 作用范围内的,否则就会报错,
 */

@Module
class MainModuleScope {
    @Provides
    @CustomSingleton
    fun getCloth(): Cloth {
        val cloth = Cloth()
        cloth.color = "蓝色"
        return cloth
    }

    /**
     * 自动关联上面的方法
     */
    @Provides
    fun getClothes(cloth: Cloth): Clothes {
        return Clothes(cloth);
    }
}

/**
 * 中间桥接层，需要手动调动注入代码
 */
@CustomSingleton
@Component(modules = [MainModuleScope::class])
interface MainComponentScope {
    fun inject(mainActivity: MockActivityScope?)
}

class MockActivityScope {
    @Inject
    lateinit var cloth: Cloth

    @Inject
    lateinit var clothes: Clothes

    init {
        val build: MainComponentScope =
            DaggerMainComponentScope.builder().mainModuleScope(MainModuleScope()).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${clothes}\n")
        print("in mock activity esXX: ${clothes.cloth.hashCode()}\n")
        print("in mock activity: ${cloth.hashCode()}\n")
    }
}

@Scope
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class CustomSingleton

fun main() {
    println("main begin2")
    val activity = MockActivityScope()
    activity.description()
}