package com.example.firstAndroid.base

import com.example.firstAndroid.functions.test.TestActivity1
import com.example.firstAndroid.functions.test.TestModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [TestModule::class])
    abstract fun testActivity1(): TestActivity1
}