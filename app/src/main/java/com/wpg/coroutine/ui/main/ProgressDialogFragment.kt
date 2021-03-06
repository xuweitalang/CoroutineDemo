package com.wpg.coroutine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.wpg.coroutine.R
import kotlinx.android.synthetic.main.fragment_progress_dialog.*

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 10:31
 * @Description:加载框
 */
class ProgressDialogFragment : DialogFragment() {
    private var messageResId: Int? = null

    /**
     * 单例
     */
    companion object {
        fun newInstance() = ProgressDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMessage.text = getString(messageResId ?: R.string.loading)
    }

    fun show(manager: FragmentManager, @StringRes message: Int, isCancelable: Boolean = true) {
        this.messageResId = message;
        this.isCancelable = isCancelable
        show(manager, "progressDialogFragment")
    }
}