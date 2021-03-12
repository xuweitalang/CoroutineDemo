package com.wpg.coroutine.ext

import com.tencent.mmkv.MMKV
import com.wpg.coroutine.R

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 9:51
 * @Description:
 */
private const val THEME = "theme"

fun getAppTheme(): Int {
    return MMKV.defaultMMKV().getInt(THEME, R.style.AppTheme)
}