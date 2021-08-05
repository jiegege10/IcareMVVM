package com.icare.jetpackmvvm.base

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener

/**
 *
 * @description:     对于写BaseApp，其实我是拒绝的，但是需要提供一个很有用的功能--在Activity/fragment中获取Application级别的ViewModel
 * 所以才硬着头皮加的，如果你不想继承BaseApp，又想获取Application级别的ViewModel功能
 * 那么你可以复制该类的代码到你的自定义Application中去，然后可以自己写获取ViewModel的拓展函数即 :
 * GetViewModelExt类的getAppViewModel方法
 * @author:         Mr.He
 * @createDate:     6/17/21 11:11 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:11 AM
 */
abstract class BaseApp : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    companion object {

        var content: Context? = null

        var preferenceName: String = "BaseApp"
    }

    override fun onCreate() {
        super.onCreate()
        AutoSize.initCompatMultiProcess(this)
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance()

            //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
            //如果没有这个需求建议不开启
            .setCustomFragment(true).onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                //                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                //                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
            }

            override fun onAdaptAfter(target: Any, activity: Activity) {
            }
        }
        content = this.applicationContext
        mAppViewModelStore = ViewModelStore()
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}