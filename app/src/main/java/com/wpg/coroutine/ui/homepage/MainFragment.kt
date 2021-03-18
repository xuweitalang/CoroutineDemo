package com.wpg.coroutine.ui.homepage

import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.MyFragmentPagerAdapter
import com.wpg.coroutine.ext.HOME_PAGE_CUT
import com.wpg.coroutine.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 14:05
 * @Description:
 */
class MainFragment : BaseFragment() {
    override fun setLayoutId(): Int = R.layout.fragment_main
    override fun initView() {
        LiveEventBus.get(HOME_PAGE_CUT).observe(this, Observer {
            homePageVp.setCurrentItem(0, true)
        })
        val homePageFragment = HomePageFragment()
        val mainProjectFragment = MainProjectFragment()
        val fragments = listOf(homePageFragment, mainProjectFragment)
        homePageVp.adapter = activity?.let { MyFragmentPagerAdapter(it, fragments) }
        homePageVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                homePageVp.isUserInputEnabled = position == 0
            }
        })
    }

    override fun initData() {
        val homePageTabLayout = arrayOf(
            context?.getString(R.string.home_page),
            context?.getString(R.string.home_project)
        )
        homePagNv.setTabLayoutData(homePageTabLayout, homePageVp)
    }
}