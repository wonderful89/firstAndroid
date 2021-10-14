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
 * <p>https://www.cnblogs.com/fxzou/p/dagger2.html
 *
 * does not allow unscoped components to use modules with scoped bindings
 * Unscoped components cannot have scoped dependencies
 * https://stackoverflow.com/questions/28170292/problems-with-singletons-when-using-component-dependencies
 */

class ClothHandler(){
    fun handle(cloth: Cloth?): Clothes {
        return Clothes(cloth!!)
    }
}

@Module
class BaseModuleDepend {
    @Singleton
    @Provides
    fun getClothesHandle(): ClothHandler {
        return ClothHandler()
    }
}

@Singleton
@Component(modules = [BaseModuleDepend::class])
interface BaseComponent {
    fun getClothesHandle() : ClothHandler
}

@Module
class MainModuleDepend {
}

@Module
class MainModuleDepend2 {
}

@CustomScopeNameA
@Component(modules = [MainModuleDepend::class], dependencies = [BaseComponent::class])
interface MainComponentDepend1 {
    fun inject(mainActivity: MockActivityDepend?)
}

@CustomScopeNameA
@Component(modules = [MainModuleDepend2::class], dependencies = [BaseComponent::class])
interface MainComponentDepend2 {
    fun inject(mainActivity: MockActivityDepend2?)
}

@Scope
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class CustomScopeNameA

//@Scope
//@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
//annotation class CustomScopeNameB

val baseComponent: BaseComponent =
    DaggerBaseComponent.builder().baseModuleDepend(BaseModuleDepend()).build();

class MockActivityDepend {

    @Inject
    lateinit var handle:ClothHandler
    init {
        val build: MainComponentDepend1 =
            DaggerMainComponentDepend1.builder().mainModuleDepend(MainModuleDepend()).baseComponent(baseComponent).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${handle}\n")
    }
}

class MockActivityDepend2 {
    @Inject
    lateinit var handle:ClothHandler

    init {
        val build: MainComponentDepend2 =
            DaggerMainComponentDepend2.builder().mainModuleDepend2(MainModuleDepend2()).baseComponent(baseComponent).build()
        build.inject(this)
    }

    fun description() {
        print("in mock activity: ${handle}\n")
    }
}

fun main() {
    println("main begin2")
    val activity = MockActivityDepend()
    activity.description()
    val activity2 = MockActivityDepend2()
    activity2.description()
}