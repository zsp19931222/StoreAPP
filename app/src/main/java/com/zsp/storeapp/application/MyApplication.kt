package com.zsp.storeapp.application

import android.R
import com.zsp.storeapp.activity.TestActivity
import io.reactivex.plugins.RxJavaPlugins
import me.andy.mvvmhabit.base.BaseApplication
import me.andy.mvvmhabit.crash.CaocConfig
import me.andy.mvvmhabit.util.ZLog


/**
 * description:
 * author:created by Andy on 2020/11/26 0026 16:42
 * email:zsp872126510@gmail.com
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        //是否开启日志打印
        ZLog.init(true)
        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
            //异常处理
            ZLog.e("RxJavaPlugins",throwable.toString())
        }
        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
            .errorDrawable(R.mipmap.sym_def_app_icon) //错误图标
            .restartActivity(TestActivity::class.java) //重新启动后的activity
            //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
            //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
            .apply()
    }
}