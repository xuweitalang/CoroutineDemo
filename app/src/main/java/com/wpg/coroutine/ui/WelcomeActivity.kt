package com.wpg.coroutine.ui

import android.os.Bundle
import com.wpg.coroutine.R
import com.wpg.coroutine.ui.base.BaseActivity
import com.wpg.coroutine.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseActivity(), CoroutineScope by MainScope() {

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() //调用CoroutineScope的cancel()函数
    }
}