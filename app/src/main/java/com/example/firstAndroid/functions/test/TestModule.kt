package com.example.firstAndroid.functions.test

import com.example.firstAndroid.base.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
interface TestModule {
    @ActivityScoped
    @Binds
    fun getPresenter(presenter: TestPresenter): TestContract.Presenter
}
