package com.wpg.coroutine.ui.homepage

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.MyFragmentPagerAdapter
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.ext.HOME_PAGE_CUT
import com.wpg.coroutine.ext.inflate
import com.wpg.coroutine.ui.base.BaseVMFragment
import com.wpg.coroutine.view.loadpage.BasePageViewForStatus
import com.wpg.coroutine.vm.HomeProjectViewModel
import kotlinx.android.synthetic.main.fragment_main_project.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainProjectFragment : BaseVMFragment<HomeProjectViewModel>() {
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private var mFragmentList = mutableListOf<Fragment>()
    override fun setLayoutId(): Int = R.layout.fragment_main_project
    override fun initVM(): HomeProjectViewModel = getViewModel()

    override fun initView() {
        llMainProjectLoadPageViewForStatus.failTextView().onClick {
            mViewModel.loadProjectClassify()
        }
        ViewPager2Delegate(vpMainProject, tlMainProject)
        vpMainProject.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private var currentPosition = 0 //当前滑动位置
            private var lastPosition = 0 //上次滑动位置
            override fun onPageScrollStateChanged(state: Int) {
                if (state == 0) {
                    if (currentPosition == lastPosition) {
                        if (currentPosition == 0) {
                            LiveEventBus.get(HOME_PAGE_CUT).post("")
                        }
                    }
                    lastPosition = currentPosition
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
            }
        })
    }

    override fun initData() {
        mViewModel.loadProjectClassify()
    }


    override fun startObserve() {
        mViewModel.run {
            mMainProjectListModel.observe(this@MainProjectFragment, Observer {
                it.loadPageStatus?.value?.let { loadPageStatus ->
                    llMainProjectLoadPageViewForStatus.visibility = View.VISIBLE
                    loadPageViewForStatus.convert(
                        llMainProjectLoadPageViewForStatus,
                        loadPageStatus
                    )
                }
                it.showSuccess?.let { list ->
                    mFragmentList.clear()
                    val classifyResponse = ClassifyResponse(
                        null,
                        0,
                        0,
                        getString(R.string.newest_project),
                        0,
                        0,
                        false,
                        0
                    )
                    llMainProjectLoadPageViewForStatus.visibility = View.GONE
                    tlMainProject.removeAllViews()
                    list.toMutableList().apply {
                        add(0, classifyResponse)
                        forEach { it ->
                            tlMainProject?.let { tlMainProject ->
                                tlMainProject.inflate(R.layout.layout_project_tab, false).apply {
                                    findViewById<TextView>(R.id.tvTabLayoutTitle).text = it.name
                                    tlMainProject.addView(this)
                                }
                            }
                            mFragmentList.add(ProjectTypeFragment.getInstance(it.id))
                        }
                    }
                    activity?.let { activity ->
                        vpMainProject.adapter = MyFragmentPagerAdapter(activity, mFragmentList)
                    }
                }
            })
        }
    }
}