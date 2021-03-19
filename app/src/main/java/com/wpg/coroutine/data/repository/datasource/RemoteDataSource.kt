package com.wpg.coroutine.data.repository.datasource

import com.wpg.coroutine.data.api.RetrofitClient
import com.wpg.coroutine.data.api.WAN_ANDROID
import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.Banner
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.data.bean.WanListResponse
import com.wpg.coroutine.data.bean.base.ResultData
import com.wpg.coroutine.utils.safeApiCall
import java.io.IOException

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 9:28
 * @Description:
 */
class RemoteDataSource {
    suspend fun getHomeArticles(page: Int) = safeApiCall(
        call = { requestHomeArticles(page) }
    )

    suspend fun getBanner() = safeApiCall(
        call = { requestBanners() }
    )

    suspend fun getStickArticles() = safeApiCall(
        call = { requestStickArticles() }
    )

    suspend fun getProjectClassify() = safeApiCall(
        call = { requestProjectClassify() }
    )

    private suspend fun requestHomeArticles(page: Int): ResultData<WanListResponse<List<Article>>> {
        val homeArticle = RetrofitClient.getInstance(WAN_ANDROID).service.getHomeArticles(page)
        if (homeArticle.errorCode == 0) {
            return ResultData.Success(homeArticle.data)
        }
        return ResultData.Error(IOException("Failed to get homeArticles${homeArticle.errorMsg}"))
    }

    private suspend fun requestBanners(): ResultData<List<Banner>> {
        val banner = RetrofitClient.getInstance(WAN_ANDROID).service.getBanner()
        if (banner.errorCode == 0) {
            return ResultData.Success(banner.data)
        }
        return ResultData.Error(IOException("Failed to get banners${banner.errorMsg}"))
    }

    private suspend fun requestStickArticles(): ResultData<List<Article>> {
        val stickArticles = RetrofitClient.getInstance(WAN_ANDROID).service.getStickArticles()
        if (stickArticles.errorCode == 0) {
            return ResultData.Success(stickArticles.data)
        }
        return ResultData.Error(IOException("Failed to get stickArticles${stickArticles.errorMsg}"))
    }

    private suspend fun requestProjectClassify(): ResultData<List<ClassifyResponse>> {
        val projectClassify = RetrofitClient.getInstance(WAN_ANDROID).service.getProjectTypes()
        if (projectClassify.errorCode == 0) {
            return ResultData.Success(projectClassify.data)
        }
        return ResultData.Error(IOException("Failed to get projectClassify${projectClassify.errorMsg}"))
    }
}