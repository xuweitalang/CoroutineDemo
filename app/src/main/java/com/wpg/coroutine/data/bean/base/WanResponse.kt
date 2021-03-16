package com.wpg.coroutine.data.bean.base

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 16:08
 * @Description:
 */
data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)