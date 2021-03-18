package com.wpg.coroutine.utils

import androidx.lifecycle.LiveData
import com.wpg.coroutine.view.loadpage.LoadPageStatus

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/17 9:28
 * @Description:
 */
class ListModel<T>(
    val showSuccess: List<T>? = null,
    val showLoading: Boolean = false,
    val showError: String? = null,
    val showEnd: Boolean = false, // 加载更多
    val isRefresh: Boolean = false, // 刷新
    val isRefreshSuccess: Boolean = true, // 是否刷新成功
    val needLogin: Boolean? = false,
    val loadPageStatus: LiveData<LoadPageStatus>? = null
)