package com.wpg.coroutine.view.loadpage

import android.app.Activity
import android.view.View

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 10:53
 * @Description:
 */

enum class LoadPageStatus {
    Loading, Fail, Empty, NoNet
}

abstract class BasePageViewForStatus {
    /**
     * 根布局
     * @param parent ViewGroup
     * @return View
     */
    abstract fun getRootView(activity: Activity): View

    /**
     * 布局中的 加载更多视图
     * @return View
     */
    abstract fun getLoadingView(LoadPageViewForStatus: LoadPageViewForStatus): View

    /**
     * 布局中的 加载失败布局
     * @return View
     */
    abstract fun getLoadFailView(LoadPageViewForStatus: LoadPageViewForStatus): View


    /**
     * 布局中的 加载空布局
     * @return View
     */
    abstract fun getLoadEmptyView(LoadPageViewForStatus: LoadPageViewForStatus): View

    /**
     * 布局中的 加载无网络布局
     * @return View
     */
    abstract fun getLoadNoNetView(LoadPageViewForStatus: LoadPageViewForStatus): View

    /**
     * 可重写此方法实现自定义逻辑
     */
    open fun convert(loadPageViewForStatus: LoadPageViewForStatus, loadPageStatus: LoadPageStatus) {
        when (loadPageStatus) {
            LoadPageStatus.Loading -> {
                getLoadingView(loadPageViewForStatus).isVisible(true)
                getLoadEmptyView(loadPageViewForStatus).isVisible(false)
                getLoadFailView(loadPageViewForStatus).isVisible(false)
                getLoadNoNetView(loadPageViewForStatus).isVisible(false)
            }
            LoadPageStatus.Empty -> {
                getLoadEmptyView(loadPageViewForStatus).isVisible(true)
                getLoadingView(loadPageViewForStatus).isVisible(false)
                getLoadFailView(loadPageViewForStatus).isVisible(false)
                getLoadNoNetView(loadPageViewForStatus).isVisible(false)
            }
            LoadPageStatus.NoNet -> {
                getLoadNoNetView(loadPageViewForStatus).isVisible(true)
                getLoadEmptyView(loadPageViewForStatus).isVisible(false)
                getLoadingView(loadPageViewForStatus).isVisible(false)
                getLoadFailView(loadPageViewForStatus).isVisible(false)
            }
            LoadPageStatus.Fail -> {
                getLoadFailView(loadPageViewForStatus).isVisible(true)
                getLoadNoNetView(loadPageViewForStatus).isVisible(false)
                getLoadEmptyView(loadPageViewForStatus).isVisible(false)
                getLoadingView(loadPageViewForStatus).isVisible(false)
            }
        }
    }

    private fun View.isVisible(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }
}