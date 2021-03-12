package com.wpg.coroutine.ui.base

import androidx.lifecycle.ViewModel

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 16:57
 * @Description:
 */
abstract class BaseVMFragment<VM : ViewModel> : BaseFragment() {
    lateinit var mViewModel: VM
    override fun onFragmentFirstVisible() {
        mViewModel = initVM()
        startObserve()
        super.onFragmentFirstVisible()
    }

    abstract fun initVM(): VM

    abstract fun startObserve()
}