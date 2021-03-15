package com.wpg.coroutine.ui.homepage

import androidx.lifecycle.ViewModel
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.HomePageAdapter
import com.wpg.coroutine.ui.base.BaseVMFragment

class HomePageFragment : BaseVMFragment<ViewModel>() {
    private val homePageAdapter = HomePageAdapter()
    override fun setLayoutId(): Int = R.layout.fragment_home_page
    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initVM(): ViewModel {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }
}