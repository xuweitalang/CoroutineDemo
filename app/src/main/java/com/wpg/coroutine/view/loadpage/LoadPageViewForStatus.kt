package com.wpg.coroutine.view.loadpage

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.wpg.coroutine.R
import com.wpg.coroutine.ext.color
import com.wpg.coroutine.ext.resourceId
import org.jetbrains.anko.*

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 9:58
 * @Description:
 */
open class LoadPageViewForStatus constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributes, defStyleAttr) {
    private lateinit var failText: TextView
    private lateinit var noNetText: TextView
    private lateinit var emptyText: TextView
    private lateinit var progressBar: ProgressBar

    init {
        verticalLayout {
            failText = textView {
                textSize = 14f
                textColor = color(R.color.text_color_primary_alpha_80)
                text = "加载错误，点我重试"
            }
            failText.layoutParams =
                LayoutParams(wrapContent, wrapContent).also { gravity = Gravity.CENTER }
            noNetText = textView {
                textSize = 14f
                textColor = color(R.color.text_color_primary_alpha_80)
                text = "网络错误,点我重试"
            }
            noNetText.layoutParams =
                LayoutParams(wrapContent, wrapContent).also { gravity = Gravity.CENTER }
            emptyText = textView {
                textSize = 14f
                textColor = color(R.color.text_color_primary_alpha_80)
                text = "还木有数据哦"
            }
            emptyText.layoutParams =
                LayoutParams(wrapContent, wrapContent).also { gravity = Gravity.CENTER }
            progressBar = progressBar {
                indeterminateTintList = ColorStateList.valueOf(
                    color(
                        TypedValue().resourceId(R.attr.colorAccent, context.theme)
                    )
                )
                indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
            }
            progressBar.layoutParams =
                LayoutParams(wrapContent, wrapContent).also { gravity = Gravity.CENTER }
        }.layoutParams = LayoutParams(matchParent, matchParent)
    }

    fun failTextView(): TextView {
        return failText
    }

    fun emptyTextView(): TextView {
        return emptyText
    }

    fun noNetTextView(): TextView {
        return noNetText
    }

    fun progressBarView(): ProgressBar {
        return progressBar
    }
}