package com.wpg.coroutine.vm

import androidx.lifecycle.MutableLiveData
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.data.repository.ArticleUserCase
import com.wpg.coroutine.data.repository.ProjectRepository
import com.wpg.coroutine.utils.ListModel
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

    private val mMainProjectListModel = MutableLiveData<ListModel<ClassifyResponse>>()

    fun loadProjectClassify() {
        launchUI {
            withContext(Dispatchers.IO) {
                projectRepository.getProjectClassify(mMainProjectListModel)
            }
        }
    }
}