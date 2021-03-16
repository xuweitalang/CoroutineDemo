package com.wpg.coroutine.view.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlin.math.max

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 16:19
 * @Description:用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件
 */
class RoundTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var delegate: RoundViewDelegate = RoundViewDelegate(this, context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (delegate.isWidthHeightEqual && width > 0 && height > 0) {
            val max = max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (delegate.isRadiusHalfHeight) {
            delegate.cornerRadius = height / 2
        } else {
            delegate.setBgSelector()
        }
    }
}