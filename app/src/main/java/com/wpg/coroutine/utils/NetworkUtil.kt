package com.wpg.coroutine.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.wpg.coroutine.app.MyApplication
import com.wpg.coroutine.data.bean.base.ResultData

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 9:30
 * @Description:   网络请求工具类
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> ResultData<T>): ResultData<T> {
    return try {
        call()
    } catch (e: Exception) {
        ResultData.Error(e)
    }
}


/**
 * 判断网络是否可用
 */
@Suppress("DEPRECATION")
fun isInternetAvailable(): Boolean {
    var result = false
    val cm =
        MyApplication.CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

        cm.getNetworkCapabilities(cm.activeNetwork).run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    } else {
        cm.activeNetworkInfo.run {
            if (type == ConnectivityManager.TYPE_WIFI) {
                result = true
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                result = true
            }
        }
    }
    return result
}