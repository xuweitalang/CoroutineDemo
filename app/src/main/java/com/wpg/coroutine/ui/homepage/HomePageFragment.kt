package com.wpg.coroutine.ui.homepage

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.HomePageAdapter
import com.wpg.coroutine.adapter.HomePageStickAdapter
import com.wpg.coroutine.ui.base.BaseVMFragment
import com.wpg.coroutine.ui.main.DetailActivity
import com.wpg.coroutine.ui.main.MainActivity
import com.wpg.coroutine.view.HomePageHeadView
import com.wpg.coroutine.view.loadpage.BasePageViewForStatus
import com.wpg.coroutine.view.loadpage.LoadPageViewForStatus
import com.wpg.coroutine.vm.HomePageViewModel
import kotlinx.android.synthetic.main.fragment_recycleview.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomePageFragment : BaseVMFragment<HomePageViewModel>(), OnLoadMoreListener {
    private val homePageAdapter = HomePageAdapter()
    private val homePageStickAdapter = HomePageStickAdapter()
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private lateinit var rootView: LoadPageViewForStatus
    private lateinit var homePageHeadView: HomePageHeadView
    override fun setLayoutId(): Int = R.layout.fragment_recycleview
    override fun initView() {
        rootView =
            (loadPageViewForStatus.getRootView(activity as MainActivity) as LoadPageViewForStatus).apply {
                failTextView().onClick { refresh() }
                noNetTextView().onClick { refresh() }
            }
        ArticleRv.apply {
            adapter = homePageAdapter
        }

        homePageHeadView = HomePageHeadView(activity, homePageStickAdapter)
        homePageAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@HomePageFragment)
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
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

    override fun initVM(): HomePageViewModel = getViewModel()

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    private fun refresh() {


    }

    override fun onLoadMore() {

    }

}