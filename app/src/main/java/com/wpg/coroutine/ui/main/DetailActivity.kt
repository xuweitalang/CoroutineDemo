package com.wpg.coroutine.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.wpg.coroutine.R
import com.wpg.coroutine.ui.base.BaseVMActivity

class DetailActivity : BaseVMActivity<ViewModel>() {

    companion object {
        const val PARAM_ARTICLE = "param_article"
    }

    override fun getLayoutId(): Int = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
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