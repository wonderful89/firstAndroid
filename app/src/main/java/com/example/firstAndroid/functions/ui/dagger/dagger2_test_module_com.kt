package com.example.firstAndroid.functions.ui.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import java.lang.annotation.Documented
import javax.inject.Singleton
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity




/** https://blog.csdn.net/qq_33994844/article/details/54971335 */
/**
 * 1:AppModule 必须提供对象的接口(proContext)
 * 2:ActivityComponentCom 必须添加dependencies=xxxx
 */
class MockContext{}

class Present{
    val ss: String = "呵呵哒---"
    constructor( context: MockContext);
}

@Module
class AppModule(context: MockContext) {
    var mContext: MockContext
    @Provides
    fun provicesContext(): MockContext {
        return mContext
    }

    init {
        mContext = context
    }
}

@Component(modules = [AppModule::class])
interface AppComponent{
    fun proContext() : MockContext
}

@Module
class ActivityModule {
    @Provides
    fun providesPresent(context: MockContext): Present {
        print("context 22 = $context \n")
        return Present(context)
    }
}

@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponentCom {
    fun injectActivity(activity: MainActivityCom?)
}

class MainActivityCom {
    @Inject
    lateinit var mPresent: Present
    init {
        val context = MockContext();
        print("context 11 = $context \n")
        val appComponent: AppComponent =
            DaggerAppComponent.builder().appModule(AppModule(context)).build()
        val activityComponent: ActivityComponentCom = DaggerActivityComponentCom.builder()
            .appComponent(appComponent)
            .activityModule(ActivityModule())
            .build()
        activityComponent.injectActivity(this)
        print( "******************" + mPresent.ss)
//        print( "******************" + mPresent.context)
    }
}

fun main() {
    println("main begin2")
    val activity = MainActivityCom()
}