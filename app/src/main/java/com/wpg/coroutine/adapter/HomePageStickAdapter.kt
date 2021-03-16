package com.wpg.coroutine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wpg.coroutine.R
import com.wpg.coroutine.data.bean.Article

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 16:10
 * @Description:
 */
class HomePageStickAdapter :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.layout_stick_article) {
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tvStickContent, item.title)
        if ((data.size - 1) == holder.layoutPosition) {
            holder.setVisible(R.id.viewDivision, false)
        }
    }
}