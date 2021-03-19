package com.wpg.coroutine.data.repository

import androidx.lifecycle.MutableLiveData
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.data.bean.base.ResultData
import com.wpg.coroutine.data.repository.datasource.RemoteDataSource
import com.wpg.coroutine.utils.ListModel
import com.wpg.coroutine.view.loadpage.LoadPageStatus

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/19 15:48
 * @Description:
 */
class ProjectRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getProjectClassify(listModel: MutableLiveData<ListModel<ClassifyResponse>>?) {
        val loadPageStatus = MutableLiveData<LoadPageStatus>()
        loadPageStatus.postValue(LoadPageStatus.Loading)
        listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))
        val projectClassify = remoteDataSource.getProjectClassify()
        if (projectClassify is ResultData.Success) {
            if (projectClassify.data.isNullOrEmpty()) {
                loadPageStatus.postValue(LoadPageStatus.Empty)
                listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))
            }
            listModel?.postValue(
                ListModel(
                    showLoading = false,
                    showSuccess = projectClassify.data
                )
            )
        } else if (projectClassify is ResultData.Error) {
            loadPageStatus.postValue(LoadPageStatus.Fail)
            listModel?.postValue(
                ListModel(
                    showLoading = false,
                    showError = projectClassify.exception.message,
                    loadPageStatus = loadPageStatus
                )
            )
        }
    }
}