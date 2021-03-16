package com.wpg.coroutine.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wpg.coroutine.R
import com.wpg.coroutine.adapter.HomePageStickAdapter
import com.wpg.coroutine.ui.main.DetailActivity
import kotlinx.android.synthetic.main.layout_banner.view.*
import org.jetbrains.anko.include
import org.jetbrains.anko.startActivity

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 16:08
 * @Description:
 */
class HomePageHeadView @JvmOverloads constructor(
    context: Context?,
    homePageStickAdapter: HomePageStickAdapter,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
    init {
        include<View>(R.layout.layout_banner)
        rvHomePageHeadView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homePageStickAdapter
        }

        homePageStickAdapter.run {
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
            setOnItemClickListener { adapter, view, position ->

                val article = data[position]
                context?.startActivity<DetailActivity>(DetailActivity.PARAM_ARTICLE to article)
            }
        }
    }
}