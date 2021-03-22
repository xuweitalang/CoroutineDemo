package com.wpg.coroutine.ui.homepage

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.HomePageAdapter
import com.wpg.coroutine.ui.base.BaseVMFragment
import com.wpg.coroutine.ui.main.DetailActivity
import com.wpg.coroutine.ui.main.MainActivity
import com.wpg.coroutine.view.loadpage.BasePageViewForStatus
import com.wpg.coroutine.view.loadpage.LoadPageViewForStatus
import com.wpg.coroutine.vm.HomeProjectViewModel
import kotlinx.android.synthetic.main.fragment_recycleview.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/22 10:58
 * @Description:
 */
class ProjectTypeFragment : BaseVMFragment<HomeProjectViewModel>(), OnLoadMoreListener {
    override fun initVM(): HomeProjectViewModel = getViewModel()
    override fun setLayoutId(): Int = R.layout.fragment_recycleview
    private val cid by lazy { arguments?.getInt(CID) }
    private val homePageAdapter = HomePageAdapter()
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private var rootView: LoadPageViewForStatus? = null
    private var i: Int = 0

    companion object {
        private const val CID = "projectCid"
        fun getInstance(cid: Int): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun startObserve() {
        mViewModel.run {
            mProjectListModel.observe(this@ProjectTypeFragment, Observer {
                if (it.isRefresh) refreshLayout.finishRefresh(it.isRefreshSuccess)
                if (it.showEnd) homePageAdapter.loadMoreModule.loadMoreEnd()
                it.loadPageStatus?.value?.let { loadPageStatus ->
                    rootView?.let { rootView ->
                        loadPageViewForStatus.convert(rootView, loadPageStatus)
                        homePageAdapter.setEmptyView(rootView)
                    }
                }
                it.showSuccess?.let { list ->
                    homePageAdapter.apply {
                        loadMoreModule.isEnableLoadMore = false
                        if (it.isRefresh) setList(list) else addData(list)
                        loadMoreModule.isEnableLoadMore = true
                        loadMoreModule.loadMoreComplete()
                    }
                }
                it.showError?.let {
                    homePageAdapter.loadMoreModule.loadMoreFail()
                }
            })
        }
    }


    override fun initView() {
        rootView =
            (loadPageViewForStatus.getRootView(activity as MainActivity) as LoadPageViewForStatus).apply {
                failTextView().onClick { refresh() }
                noNetTextView().onClick { refresh() }
            }
        ArticleRv.adapter = homePageAdapter
        homePageAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@ProjectTypeFragment)
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
            setOnItemClickListener { adapter, view, position ->
                val article = data[position]
                startActivity<DetailActivity>(DetailActivity.PARAM_ARTICLE to article)
            }
        }
        refreshLayout.apply {
            setOnRefreshListener { refresh() }
            setEnableLoadMore(false)
        }
    }

    override fun initData() {
        if (i != 0) {
            //viewPager缓存4个界面，界面重新加载的时候清空数据重新获取
            homePageAdapter.setList(null)
        }
        refresh()
        i++
    }

    override fun onLoadMore() {
        cid?.let { mViewModel.loadProjectArticles(false, it) }
    }

    private fun refresh() {
        cid?.let { mViewModel.loadProjectArticles(true, it) }
    }
}