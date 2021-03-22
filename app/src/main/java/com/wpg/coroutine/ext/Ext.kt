package com.wpg.coroutine.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 10:02
 * @Description:
 */
const val SET_THEME = "set_theme"
const val HOME_PAGE_CUT = "home_page_cut"

//获取主题属性id
fun TypedValue.resourceId(resId: Int, theme: Resources.Theme): Int {
    theme.resolveAttribute(resId, this, true)
    return this.resourceId
}

//加载子布局
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = true): View {
    if (layout == -1) {
        return this
    }
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

//获取颜色
fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)
fun View.color(colorRes: Int) = context.color(colorRes)

//设置颜色
fun Context.text(stringRes: Int) = this.resources.getText(stringRes)
fun View.text(stringRes: Int) = context.text(stringRes)

/***
 * 带延迟过滤的点击事件View扩展
 * @param time Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
    setOnClickListener {
        block(it as T)
    }
}

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentTime = System.currentTimeMillis()
    if (currentTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentTime
    return flag
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else -601
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 600
    set(value) {
        setTag(1123461123, value)
    }

fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
