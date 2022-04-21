package com.example.firstAndroid.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.qqz.baselib.QZBaseLib

/**
 * ================================================
 * [ActivityLifecycleCallbacksImpl] 可用来代替在 BaseActivity 中加入适配代码的传统方式
 * [ActivityLifecycleCallbacksImpl] 这种方案类似于 AOP, 面向接口, 侵入性低, 方便统一管理, 扩展性强, 并且也支持适配三方库的 [Activity]
 *
 *
 * Created by JessYan on 2018/8/8 14:32
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class ActivityLifecycleCallbacksImpl() :
    Application.ActivityLifecycleCallbacks {

    companion object {
        val TAG = "ActivityLifecycle"
    }
    /**
     * 屏幕适配逻辑策略类
     */
//    private var mAutoAdaptStrategy: AutoAdaptStrategy?

    /**
     * 让 Fragment 支持自定义适配参数
     */
//    private var mFragmentLifecycleCallbacks: FragmentLifecycleCallbacksImpl? = null
//    private var mFragmentLifecycleCallbacksToAndroidx: FragmentLifecycleCallbacksImplToAndroidx? =

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated")
        QZBaseLib.updateDensityUI(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

    init {

    }
}