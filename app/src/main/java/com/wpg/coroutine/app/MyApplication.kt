package com.wpg.coroutine.app

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.util.TypedValue
import com.chad.library.adapter.base.module.LoadMoreModuleConfig.defLoadMoreView
import com.coder.zzq.smartshow.core.SmartShow
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.wpg.coroutine.BuildConfig
import com.wpg.coroutine.R
import com.wpg.coroutine.ext.getNightMode
import com.wpg.coroutine.ext.resourceId
import com.wpg.coroutine.utils.ActivityHelper
import com.wpg.coroutine.view.CustomLoadMoreView
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import kotlin.properties.Delegates

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 13:35
 * @Description:
 */
class MyApplication : Application() {
    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        ActivityHelper.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MyApplication)
            //配置依赖
            modules(appModule)
        }

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(
                TypedValue().resourceId(R.attr.colorPrimary, context.theme),
                TypedValue().resourceId(R.attr.textColorPrimary, context.theme)
            ) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        defLoadMoreView = CustomLoadMoreView()

        MMKV.initialize(this)
        LiveEventBus.config()
        SmartShow.init(this)
        //夜间模式
        (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).nightMode = getNightMode()
    }
}