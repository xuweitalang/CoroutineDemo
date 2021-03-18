package com.wpg.coroutine.ext

import androidx.appcompat.app.AppCompatDelegate
import com.tencent.mmkv.MMKV
import com.wpg.coroutine.R

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 9:51
 * @Description:
 */
private const val THEME = "theme"
private const val KEY_NIGHT_MODE = "key_night_mode"
fun getAppTheme(): Int {
    return MMKV.defaultMMKV().getInt(THEME, R.style.AppTheme)
}

fun setNightMode(mode: Int) {
    MMKV.defaultMMKV().putInt(KEY_NIGHT_MODE, mode)
}

fun getNightMode(): Int = MMKV.defaultMMKV().getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)