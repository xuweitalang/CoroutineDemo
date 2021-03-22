package com.wpg.coroutine.data.repository

import androidx.lifecycle.MutableLiveData
import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.base.ResultData
import com.wpg.coroutine.data.repository.datasource.RemoteDataSource
import com.wpg.coroutine.utils.ListModel
import com.wpg.coroutine.view.loadpage.LoadPageStatus
import java.net.UnknownHostException

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/17 9:02
 * @Description:
 */
class ArticleUserCase(var remoteDataSource: RemoteDataSource) {
    private var currentPage = 0
    private var currentKeyword = ""

    sealed class ArticleType {
        object Home : ArticleType()                 // 首页
        object Square : ArticleType()               // 广场
        object LatestProject : ArticleType()        // 最新项目
        object ProjectDetailList : ArticleType()    // 项目列表
        object Collection : ArticleType()           // 收藏
        object SystemType : ArticleType()           // 体系分类
        object Blog : ArticleType()                 // 公众号
        object Share : ArticleType()                 // 分享
        object Search : ArticleType()                 // 文章搜索
    }

    suspend fun getHomeArticleList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>,
        loadPageStatus: MutableLiveData<LoadPageStatus>
    ) = getArticleList(ArticleType.Home, isRefresh, listModel, loadPageStatus)

    suspend fun getLatestProjectList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>
    ) = getArticleList(ArticleType.LatestProject, isRefresh, listModel, loadPageStatus)

    suspend fun getProjectTypeDetailList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>,
        cid: Int
    ) = getArticleList(ArticleType.ProjectDetailList, isRefresh, listModel, loadPageStatus, cid)

    private suspend fun getArticleList(
        articleType: ArticleType,
        refresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>,
        cid: Int = 0,
        keywords: String = ""
    ) {
        loadPageStatus.postValue(LoadPageStatus.Loading)
        listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))
        if (refresh) {
            currentPage =
                if (articleType is ArticleType.ProjectDetailList || articleType is ArticleType.Share) 1 else 0
        }
        var result = when (articleType) {
            ArticleType.Home -> remoteDataSource.getHomeArticles(currentPage)
            else -> null
        }
        if (result is ResultData.Success) {
            val data = result.data
            if (data.datas.isNullOrEmpty() && currentPage == if (articleType is ArticleType.ProjectDetailList || articleType is ArticleType.Share) 1 else 0) {
                //显示空页面
                loadPageStatus.postValue(LoadPageStatus.Empty)
                listModel?.postValue(
                    ListModel(
                        isRefreshSuccess = false,
                        loadPageStatus = loadPageStatus,
                        isRefresh = refresh
                    )
                )
                return
            }

            if (data.offset >= data.pageCount) { //最后一页
                listModel?.postValue(
                    ListModel(
                        isRefreshSuccess = true,
                        isRefresh = refresh,
                        showEnd = true
                    )
                )
                return
            }

            currentPage++
            listModel?.postValue(
                ListModel(
                    isRefreshSuccess = true,
                    isRefresh = refresh,
                    showSuccess = data.datas
                )
            )
        } else if (result is ResultData.Error) {
            if (result.exception is UnknownHostException) {
                if (currentPage == 0) loadPageStatus.postValue(LoadPageStatus.NoNet)
            } else {
                if (currentPage == 0) loadPageStatus.postValue(LoadPageStatus.Fail)
            }
            listModel?.postValue(
                ListModel(
                    isRefresh = refresh,
                    isRefreshSuccess = false,
                    showError = result.exception.message,
                    loadPageStatus = loadPageStatus
                )
            )
        }
    }
}