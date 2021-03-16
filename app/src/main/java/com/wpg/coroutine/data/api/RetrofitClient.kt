package com.wpg.coroutine.data.api

import com.wpg.coroutine.ext.CookieClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 15:06
 * @Description:
 */
class RetrofitClient private constructor(hostType: Int) : BaseRetrofitClient() {
    //初始化属性时会有双重锁检查，保证该值只在一个线程中计算，并且所有线程会得到相同的值。
    val service by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        getService(ApiService::class.java, hostType)
    }

    companion object {
        @Volatile
        private var instance: RetrofitClient? = null
        fun getInstance(hostType: Int) = instance ?: synchronized(this) {
            instance ?: RetrofitClient(hostType).also { instance = it }
        }
    }

    //Retrofit扩展
    override fun retrofitBuilder(build: Retrofit.Builder) {
    }

    //OKHttpClient扩展
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.cookieJar(CookieClass.cookieJar)
    }
}