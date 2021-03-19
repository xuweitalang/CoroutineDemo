package com.wpg.coroutine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wpg.coroutine.R
import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.ext.htmlToSpanned
import com.wpg.coroutine.ext.text
import kotlinx.android.synthetic.main.item_home_page.view.*

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/15 9:18
 * @Description:
 */
class HomePageAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_home_page),
    LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.itemView.apply {
            tvHomePageItemAuthor.text = when {
                !item.author.isNullOrEmpty() -> item.author
                !item.shareUser.isNullOrEmpty() -> item.shareUser
                else -> text(R.string.anonymous)
            }
            tvHomePageItemContent.text = item.title.htmlToSpanned()
            tvHomePageItemType.text = when {
                !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    "${item.superChapterName.htmlToSpanned()}.${item.chapterName.htmlToSpanned()}"
                item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    item.chapterName.htmlToSpanned()
                !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                    item.superChapterName.htmlToSpanned()
                else -> ""
            }
            tvHomePageItemDate.text = item.niceDate
        }
    }
}