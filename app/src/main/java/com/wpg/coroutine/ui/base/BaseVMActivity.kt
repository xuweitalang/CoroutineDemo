package com.wpg.coroutine.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 16:58
 * @Description:
 */
abstract class BaseVMActivity<VM : ViewModel> : BaseActivity() {
    private lateinit var viewModel: VM

    override fun initActivity(savedInstanceState: Bundle?) {
        viewModel = initVM()
        startObserve()
        super.initActivity(savedInstanceState)
    }

    abstract fun initVM(): VM

    abstract fun startObserve()
}
