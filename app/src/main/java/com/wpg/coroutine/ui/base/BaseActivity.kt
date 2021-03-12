package com.wpg.coroutine.ui.base

import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.wpg.coroutine.R
import com.wpg.coroutine.ext.getAppTheme
import com.wpg.coroutine.ext.resourceId
import com.wpg.coroutine.ui.main.ProgressDialogFragment


/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 9:50
 * @Description:
 */
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var dialogFragment: ProgressDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initImmersionBar()
        setSmartSnackBar()
        initActivity(savedInstanceState)
    }

    open fun initImmersionBar() {
        immersionBar {
            statusBarColor(
                TypedValue().resourceId(
                    R.attr.colorPrimary,
                    theme
                )
            ).autoStatusBarDarkModeEnable(true, 0.2f)
        }
    }

    open fun setSmartSnackBar() {
        SmartSnackbar.setting()
            .backgroundColorRes(TypedValue().resourceId(R.attr.colorAccent, theme))
            .dismissOnLeave(true)
    }

    open fun initActivity(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initData()
    }

    /**
     * 显示加载框
     */
    open fun showProgressDialog(@StringRes message: Int) {
        if (!this::dialogFragment.isInitialized) { //没有初始化
            dialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!dialogFragment.isAdded) {
            dialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏对话框
     */
    open fun dismissProgressDialog() {
        if (this::dialogFragment.isInitialized && dialogFragment.isVisible) {
            dialogFragment.dismissAllowingStateLoss()
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

}

