package com.example.kotlin.app

import com.example.agroapp.R
import com.example.agroapp.ui.main.activity.MainActivity
import io.reactivex.plugins.RxJavaPlugins
import me.goldze.mvvmhabit.BuildConfig
import me.goldze.mvvmhabit.base.BaseApplication
import me.goldze.mvvmhabit.crash.CaocConfig
import me.goldze.mvvmhabit.utils.KLog

/**
 * author : jiangxue
 * date : 2023/5/15 9:09
 * description :
 */
class AppApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        instances = this
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG)
        initCrash()
        setRxJavaErrorHandler()
    }

    private fun initCrash() {
//是否开启日志打印
        KLog.init(true)
        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity::class.java) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply()
    }

    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable: Throwable -> throwable.printStackTrace() }
    }

    companion object {
        var instances: AppApplication? = null
    }
}