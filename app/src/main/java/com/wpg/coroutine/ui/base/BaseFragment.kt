package com.wpg.coroutine.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.wpg.coroutine.ui.main.ProgressDialogFragment

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 15:09
 * @Description:
 */
abstract class BaseFragment : Fragment() {
    private lateinit var progressDialogFragment: ProgressDialogFragment
    private var isLoaded: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutId(), container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            onFragmentFirstVisible()
            isLoaded = true
        }
    }

    open fun onFragmentFirstVisible() {
        initView()
        initData()
    }

    /**
     * 显示loading框
     */
    open fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            activity?.supportFragmentManager?.let {
                progressDialogFragment.show(it, message, false)
            }
        }
    }

    /**
     * 隐藏loading框
     */
    open fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismiss()
        }
    }

    abstract fun setLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

}