package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
import javax.inject.Singleton
import com.example.firstAndroid.MainActivity

import dagger.Subcomponent




/**
 * <p>https://www.cnblogs.com/fxzou/p/dagger2.html
 *
 * 子组件的声明方式由 @Component 改为 @Subcomponent
 * 在父组件中要声明一个返回值为子组件的方法,当子组件需要什么Module时,就在该方法中添加该类型的参数
 *
 * 子组件可以获取到所有父组件能提供的类型。（子类继承父类，但是子组件是在父组件中生成有点不太对。。）
 */

/**
 * 子组件
 */

@Module
class MainModuleDependSub {
}

@Subcomponent(modules = [MainModuleDependSub::class])
interface SubMainComponent {
    fun inject(activity: MockActivitySubModule?)
}

/**
 * 父组件
 */
@Module
class BaseModuleDepend2 {
    @Singleton
    @Provides
    fun getClothesHandle(): ClothHandler {
        return ClothHandler()
    }
}

@Singleton
@Component(modules = [BaseModuleDepend2::class])
interface BaseComponent2 {
    fun getClothesHandle() : ClothHandler

    //@Subcomponent使用的声明方式,声明一个返回值为子组件的方法,子组件需要什么Module,就在方法参数中添加什么
    fun getSubMainComponent(module: MainModuleDependSub?): SubMainComponent?
}

val baseComponent2: BaseComponent2 =
    DaggerBaseComponent2.builder().baseModuleDepend2(BaseModuleDepend2()).build();

class MockActivitySubModule {

    @Inject
    lateinit var handle:ClothHandler
    init {
        val build: SubMainComponent? =
            baseComponent2.getSubMainComponent(MainModuleDependSub())
        build!!.inject(this)
    }

    fun description() {
        print("in mock activity: ${handle}\n")
    }
}


fun main() {
    println("main begin2")
    val activity = MockActivitySubModule()
    activity.description()
}