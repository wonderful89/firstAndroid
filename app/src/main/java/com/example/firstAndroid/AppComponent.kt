package com.example.firstAndroid

import android.app.Application
import com.example.firstAndroid.base.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import android.content.Context
import dagger.Binds
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Application Module
 */


@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}

/**
 * BModule: 业务 module
 */
@Module(
    includes = [
        ApplicationModule::class,
        AndroidInjectionModule::class
//        RetrofitModule::class,
//        ApiServiceModule::class,
//        DBModule::class,
//        ReceiverBuilderModule::class,
//        BServiceBuilderModule::class
    ]
)
interface BModule

/**
 * TModule
 */
@Module(includes = [ActivityBindingModule::class])
interface TModule

/**
 * AppComponent2
 */
@Singleton
@Component(
    modules = [
        BModule::class,
        TModule::class
    ]
)
interface FirstAndroidAppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder


        fun build(): FirstAndroidAppComponent
    }
}