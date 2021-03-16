package com.wpg.coroutine.data.api

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 14:56
 * @Description:
 */
const val WAN_ANDROID = 1
const val WANANDROIDURL = "https://www.wanandroid.com"
const val GANK_IO = 2
const val GANKIOURL = "https://gank.io"

fun getHost(hostType: Int): String {
    lateinit var host: String
    when (hostType) {
        WAN_ANDROID -> host = WANANDROIDURL
        GANK_IO -> host = GANKIOURL
    }
    return host
}