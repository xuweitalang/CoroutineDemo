package com.wpg.coroutine.ui.homepage

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.HomePageAdapter
import com.wpg.coroutine.adapter.HomePageStickAdapter
import com.wpg.coroutine.adapter.ImageAdapter
import com.wpg.coroutine.ui.base.BaseVMFragment
import com.wpg.coroutine.ui.main.DetailActivity
import com.wpg.coroutine.ui.main.MainActivity
import com.wpg.coroutine.view.HomePageHeadView
import com.wpg.coroutine.view.loadpage.BasePageViewForStatus
import com.wpg.coroutine.view.loadpage.LoadPageViewForStatus
import com.wpg.coroutine.vm.HomePageViewModel
import kotlinx.android.synthetic.main.fragment_recycleview.*
import kotlinx.android.synthetic.main.layout_banner.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class HomePageFragment : BaseVMFragment<HomePageViewModel>(), OnLoadMoreListener {
    private val homePageAdapter = HomePageAdapter()
    private val homePageStickAdapter = HomePageStickAdapter()
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private var rootView: LoadPageViewForStatus? = null
    private lateinit var homePageHeadView: HomePageHeadView
    override fun setLayoutId(): Int = R.layout.fragment_recycleview
    override fun initVM(): HomePageViewModel = getViewModel()
    override fun initView() {
        rootView =
            (loadPageViewForStatus.getRootView(activity as MainActivity) as LoadPageViewForStatus).apply {
                failTextView().onClick { refresh() }
                noNetTextView().onClick { refresh() }
            }
        ArticleRv.apply {
            adapter = homePageAdapter
        }

        homePageAdapter.apply {
            homePageHeadView = HomePageHeadView(activity, homePageStickAdapter)
            loadMoreModule.setOnLoadMoreListener(this@HomePageFragment)
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
            activity?.let { addHeaderView(homePageHeadView) }
            setOnItemClickListener { adapter, view, position ->
                startActivity<DetailActivity>(DetailActivity.PARAM_ARTICLE to data[position])
            }
        }
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener { refresh() }
    }

    override fun initData() {
        refresh()
    }

    override fun startObserve() {
        mViewModel.run {
            mListModel.observe(this@HomePageFragment, Observer {
                if (it.isRefresh) refreshLayout.finishRefresh(it.isRefreshSuccess)
                if (it.showEnd) homePageAdapter.loadMoreModule.loadMoreEnd()
                it.loadPageStatus?.value?.let { loadPageStatus ->
                    rootView?.let { rootView ->
                        loadPageViewForStatus.convert(rootView, loadPageStatus = loadPageStatus)
                        homePageAdapter.setEmptyView(rootView)
                    }
                }
                it.showSuccess?.let { list ->
                    homePageAdapter.run {
                        if (it.isRefresh) setList(list) else addData(list)
                        loadMoreModule.isEnableLoadMore = true
                        loadMoreModule.loadMoreComplete()
                        mViewModel.loadBanner() //列表加载成功后再加载banner
                    }
                }
                it.showError.let { errorMsg ->
                    homePageAdapter.loadMoreModule.loadMoreFail()
                    Timber.e(errorMsg)
//                    SmartToast.show(errorMsg)
                }
            })
            mBanner.observe(this@HomePageFragment, Observer { banners ->
                banner?.adapter = activity?.let { ImageAdapter(banners, it) }
                mViewModel.loadStickArticles()
            })
            mStickArticles.observe(this@HomePageFragment, Observer { articles ->
                homePageStickAdapter.setList(articles)
            })
        }
    }

    private fun refresh() {
        mViewModel.loadHomeArticles(true)
    }

    override fun onLoadMore() {
        mViewModel.loadHomeArticles(false)
    }

    override fun onStart() {
        super.onStart()
        banner?.start()
    }

    override fun onStop() {
        super.onStop()
        banner?.stop()
    }

}