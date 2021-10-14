package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import java.lang.annotation.Documented
import javax.inject.Singleton

/**
 * <p>https://www.cnblogs.com/fxzou/p/dagger2.html
 * <p>步骤1：查找Module中是否存在创建该类的方法。
 *
 * 步骤2：若存在创建类方法，查看该方法是否存在参数
 *
 * 步骤2.1：若存在参数，则按从步骤1开始依次初始化每个参数
 *
 * 步骤2.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束
 * 步骤3：若不存在创建类方法，则查找Inject注解的构造函数，看构造函数是否存在参数
 * 步骤3.1：若存在参数，则从步骤1开始依次初始化每个参数
 * 步骤3.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束
 */

class Clothes(val cloth: Cloth) {

    override fun toString(): String {
        return cloth.color + "衣服"
    }
}

@Module
class MainModule2 {
    @Provides
    @Singleton
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
@Singleton
@Component(modules = [MainModule2::class])
interface MainComponent2 {
    fun inject(mainActivity: MockActivity4?)
}

class MockActivity4 {
    @Inject
    lateinit var cloth: Cloth

    @Inject
    lateinit var clothes: Clothes

    init {
        val build: MainComponent2 =
            DaggerMainComponent2.builder().mainModule2(MainModule2()).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${clothes}\n")
        print("in mock activity esXX: ${clothes.cloth.hashCode()}\n")
        print("in mock activity: ${cloth.hashCode()}\n")
    }
}

fun main() {
    println("main begin2")
    val activity = MockActivity4()
    activity.description()
}