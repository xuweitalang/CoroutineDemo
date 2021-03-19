package com.wpg.coroutine.data.api

import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.Banner
import com.wpg.coroutine.data.bean.ClassifyResponse
import com.wpg.coroutine.data.bean.WanListResponse
import com.wpg.coroutine.data.bean.base.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 15:20
 * @Description:
 */
interface ApiService {

    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<WanListResponse<List<Article>>>

    /**
     * 获取banner数据
     */
    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<MutableList<Banner>>

    /**
     * 获取置顶文章数据
     */
    @GET("/article/top/json")
    suspend fun getStickArticles(): WanResponse<List<Article>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    suspend fun getProjectTypes(): WanResponse<MutableList<ClassifyResponse>>

}