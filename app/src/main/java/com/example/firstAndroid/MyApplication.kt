package com.example.firstAndroid

import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.example.firstAndroid.base.ActivityLifecycleCallbacksImpl
import com.qqz.baselib.QZBaseLib
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.beta.upgrade.UpgradeStateListener
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : DaggerApplication() {

    companion object{
        const val tag = "MyApplication"
        const val buglyAppId = "d661688be4"
        const val buglyAppChannel = "DEBUG1"

        @JvmStatic // 全局的静态Application
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(tag, "onCreate")
        initSync()
        Thread {
            initAsync()
        }.start()
        Log.e("App", "QZBaseLib version = ${QZBaseLib.version}")
        val mActivityLifecycleCallbacks =
            ActivityLifecycleCallbacksImpl()
        this.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        QZBaseLib.printResolution(this)
        QZBaseLib.initWithApp(this)
    }

    private fun initSync(){
        initBugly()
        initX5Kit()
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    open fun isDebug(): Boolean = true

    private fun initAsync(){

    }

    private fun initBugly(){
        Log.d(tag, "初始化bugLy X")
        /**** Beta高级设置*****/
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        /**** Beta高级设置 */
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        Beta.autoInit = true

        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = true

        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        Beta.initDelay = 1 * 1000.toLong()

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        Beta.largeIconId = R.mipmap.ic_launcher

        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher

        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher

        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = false

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity::class.java)

        //监听安装包下载状态
        /**
         * 设置自定义升级对话框UI布局
         * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         * 标题：beta_title，如：android:tag="beta_title"
         * 升级信息：beta_upgrade_info  如： android:tag="beta_upgrade_info"
         * 更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
         * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         * 详见layout/upgrade_dialog.xml
         */
//        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        /**
         * 设置自定义tip弹窗UI布局
         * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         * 标题：beta_title，如：android:tag="beta_title"
         * 提示信息：beta_tip_message 如： android:tag="beta_tip_message"
         * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         * 详见layout/tips_dialog.xml
         */
//        Beta.tipsDialogLayoutId = R.layout.tips_dialog;
        /**
         * 如果想监听升级对话框的生命周期事件，可以通过设置OnUILifecycleListener接口
         * 回调参数解释：
         * context - 当前弹窗上下文对象
         * view - 升级对话框的根布局视图，可通过这个对象查找指定view控件
         * upgradeInfo - 升级信息
         */
        /*  Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onCreate");
                // 注：可通过这个回调方式获取布局的控件，如果设置了id，可通过findViewById方式获取，如果设置了tag，可以通过findViewWithTag，具体参考下面例子:

                // 通过id方式获取控件，并更改imageview图片
                ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                imageView.setImageResource(R.mipmap.ic_launcher);

                // 通过tag方式获取控件，并更改布局内容
                TextView textView = (TextView) view.findViewWithTag("textview");
                textView.setText("my custom text");

                // 更多的操作：比如设置控件的点击事件
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onStart");
            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onResume");
            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onPause");
            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onStop");
            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.d(TAG, "onDestory");
            }
        };*/
        /**
         * 自定义Activity参考，通过回调接口来跳转到你自定义的Actiivty中。
         */
        /* Beta.upgradeListener = new UpgradeListener() {

            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), UpgradeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "没有更新", Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        //监听安装包下载状态
        Beta.downloadListener =
            object : DownloadListener {
                override fun onReceive(downloadTask: DownloadTask) {
                    Log.d(tag, "downloadListener receive apk file")
                }

                override fun onCompleted(downloadTask: DownloadTask) {
                    Log.d(
                        tag,
                        "downloadListener download apk file success"
                    )
                }

                override fun onFailed(
                    downloadTask: DownloadTask,
                    i: Int,
                    s: String
                ) {
                    Log.d(tag, "downloadListener download apk file fail")
                }
            }

        //监听APP升级状态

        //监听APP升级状态
        Beta.upgradeStateListener = object : UpgradeStateListener {
            override fun onUpgradeFailed(b: Boolean) {
                Log.d(tag, "upgradeStateListener upgrade fail")
            }

            override fun onUpgradeSuccess(b: Boolean) {
                Log.d(tag, "upgradeStateListener upgrade success")
            }

            override fun onUpgradeNoVersion(b: Boolean) {
                Log.d(
                    tag,
                    "upgradeStateListener upgrade has no new version"
                )
            }

            override fun onUpgrading(b: Boolean) {
                Log.d(tag, "upgradeStateListener upgrading")
            }

            override fun onDownloadCompleted(b: Boolean) {
                Log.d(
                    tag,
                    "upgradeStateListener download apk file success"
                )
            }
        }

        /**
         * 已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能;
         * init方法会自动检测更新，不需要再手动调用Beta.checkUpdate(),如需增加自动检查时机可以使用Beta.checkUpdate(false,false);
         * 参数1： applicationContext
         * 参数2：appId
         * 参数3：是否开启debug
         */
        /**
         * 已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能;
         * init方法会自动检测更新，不需要再手动调用Beta.checkUpdate(),如需增加自动检查时机可以使用Beta.checkUpdate(false,false);
         * 参数1： applicationContext
         * 参数2：appId
         * 参数3：是否开启debug
         */
        Bugly.init(applicationContext,
            buglyAppId, false)
        Bugly.setAppChannel(applicationContext,
            buglyAppChannel
        )
    }

    private fun initX5Kit() {
        Log.d(tag, "初始化X5Kit")
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true)
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(tag,"开启TBS===X5加速成功")
            }

            override fun onCoreInitFinished() {
                Log.d(tag, "开启TBS===X5加速失败")
            }
        }
        //x5内核初始化接口
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d("$tag", "注入代码")
        return DaggerFirstAndroidAppComponent.builder().application(this).build()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
}