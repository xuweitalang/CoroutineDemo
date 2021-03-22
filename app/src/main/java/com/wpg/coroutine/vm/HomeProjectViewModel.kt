package com.wpg.coroutine.vm

import androidx.lifecycle.MutableLiveData
import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.data.repository.ArticleUserCase
import com.wpg.coroutine.data.repository.ProjectRepository
import com.wpg.coroutine.utils.ListModel
import com.wpg.coroutine.view.loadpage.LoadPageStatus
import com.wpg.coroutine.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/19 15:41
 * @Description:
 */
class HomeProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val articleUserCase: ArticleUserCase
) : BaseViewModel() {

    val mMainProjectListModel = MutableLiveData<ListModel<ClassifyResponse>>()
    val mProjectListModel = MutableLiveData<ListModel<Article>>()
    val mProjectLoadPageStatus = MutableLiveData<LoadPageStatus>()

    fun loadProjectClassify() {
        launchUI {
            withContext(Dispatchers.IO) {
                projectRepository.getProjectClassify(mMainProjectListModel)
            }
        }
    }

    fun loadProjectArticles(isRefresh: Boolean = false, cid: Int = 0) = launchUI {
        withContext(Dispatchers.IO) {
            when (cid) {
                0 -> {
                    articleUserCase.getLatestProjectList(
                        isRefresh,
                        mProjectListModel,
                        mProjectLoadPageStatus
                    )
                }
                else -> {
                    articleUserCase.getProjectTypeDetailList(
                        isRefresh,
                        mProjectListModel,
                        mProjectLoadPageStatus,
                        cid
                    )
                }
            }
        }
    }
}