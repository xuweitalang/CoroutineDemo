package com.wpg.coroutine.view.loadpage

import android.app.Activity
import android.view.View

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 10:44
 * @Description:
 * 可以自己定义列表加载界面（xml也可）
override fun getRootView(parent: ViewGroup): View =
parent.getItemView(R.layout.brvah_quick_view_load_more)

override fun getLoadingView(holder: BaseViewHolder): View =
holder.getView(R.id.load_more_loading_view)

override fun getLoadComplete(holder: BaseViewHolder): View =
holder.getView(R.id.load_more_load_complete_view)

override fun getLoadEndView(holder: BaseViewHolder): View =
holder.getView(R.id.load_more_load_end_view)

override fun getLoadFailView(holder: BaseViewHolder): View =
holder.getView(R.id.load_more_load_fail_view)
 */
class SimplePageViewForStatus : BasePageViewForStatus() {
    override fun getRootView(activity: Activity): View {
        return LoadPageViewForStatus(activity)
    }

    override fun getLoadingView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.progressBarView()
    }

    override fun getLoadFailView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.failTextView()
    }

    override fun getLoadEmptyView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.emptyTextView()
    }

    override fun getLoadNoNetView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.noNetTextView()
    }
}