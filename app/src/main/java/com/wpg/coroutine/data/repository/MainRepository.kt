package com.wpg.coroutine.data.repository

import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.Banner
import com.wpg.coroutine.data.bean.base.ResultData
import com.wpg.coroutine.data.repository.datasource.RemoteDataSource

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 9:27
 * @Description:
 */
class MainRepository(private var homeRemoteDataSource: RemoteDataSource) {
    suspend fun getBanner(): ResultData<List<Banner>> {
        return homeRemoteDataSource.getBanner()
    }

    suspend fun getStickArticles(): ResultData<List<Article>> {
        return homeRemoteDataSource.getStickArticles()
    }
}