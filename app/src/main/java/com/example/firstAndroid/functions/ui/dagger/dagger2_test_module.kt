package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class Cloth {
    var color: String? = null

    override fun toString(): String {
        return color + "布料"
    }
}

/**
 * 作为代理，提供外部实现 依赖类 的方法。
 * 同一种类型，这里不能直接定义2个，否则它不知道该找哪一个
 * 项目中我们会用到别人的jar包,我们无法修改别人的源码,就更别说在人家的类上添加注解了,所以我们只能通过Module类来提供.
 */
@Module
class MainModule {
    @Provides
    fun getCloth(): Cloth {
        val cloth = Cloth()
        cloth.color = "红色"
        return cloth
    }

//    @Provides
//    fun getCloth2(): Cloth {
//        val cloth = Cloth()
//        cloth.color = "蓝色"
//        return cloth
//    }
}

/**
 * 中间桥接层，需要手动调动注入代码
 */
@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MockActivity?)
}

class MockActivity {

    @Inject
    lateinit var cloth: Cloth

    init {
        val build: MainComponent = DaggerMainComponent.builder().mainModule(MainModule()).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${cloth.color}")
    }
}

fun main() {
    println("main begin2")
    val activity = MockActivity()
    activity.description()
}