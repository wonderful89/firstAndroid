package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider

/**
 * onCreate()方法调用完成后,Component实例就会因为没有被引用而被垃圾回收器回收.其中传入给Component实例的Module实例也会一同被回收,这也就能说明不同的Component实例之间是没有联系的(Component依赖除外).这里需要注意的是,使用Lazy和Provider时,与该依赖对象有关的Module实例会被Lazy和Provider引用,所以该Module实例不会被垃圾回收器回收
 */
class MockActivityLazy {
//    @Inject
//    lateinit var shoeX1: ShoeX1
//    @Inject
//    lateinit var shoeX2: ShoeX2

    /** 第一次使用的时候才会被初始化 */
    @Inject
    lateinit var shoeX1: dagger.Lazy<ShoeX1>

//    @Inject
//    lateinit var shoeX2: Provider<ShoeX2> // Provider声明方式

    /** 调用的注入时候，就被初始化了 */
    @Inject
    lateinit var shoeX2: ShoeX2

    init {
        val component: InjectComponentLazy =
            DaggerInjectComponentLazy.builder().injectModuleLazy(InjectModuleLazy()).build()
        component.inject(this);
    }

    /**
     * 输出 Log:
     * <p>
     * init ShoeX2
     *
     * init ShoeX1
     *
     * in description ShoeX1
     *
     * in description ShoeX2
     */
    fun description() {
        shoeX1.get().description()
        shoeX2.description()
    }
}

class ShoeX1 {
    init {
        print("init ShoeX1 ${hashCode()} \n")
    }

    fun description() {
        print("in description ShoeX1 \n")
    }
}

class ShoeX2 {
    init {
        print("init ShoeX2 ${hashCode()} \n")
    }

    fun description() {
        print("in description ShoeX2 \n")
    }
}

@Module
class InjectModuleLazy {
    @Provides
    fun getShoe(): ShoeX1 {
        return ShoeX1()
    }

    @Provides
    fun getShoe2(): ShoeX2 {
        return ShoeX2()
    }
}

@Component(modules = [InjectModuleLazy::class])
interface InjectComponentLazy {
    fun inject(mainClass: MockActivityLazy)
}

fun main() {
    val activity = MockActivityLazy()
    activity.description()
}