package com.wpg.coroutine.data.api

import android.annotation.SuppressLint
import com.wpg.coroutine.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 14:14
 * @Description:
 */
abstract class BaseRetrofitClient {
    companion object {
        const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logger.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logger.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(logger)
                .addInterceptor(mLoggingInterceptor)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            handleBuilder(builder)
            return builder.build()
        }

    @SuppressLint("BinaryOperationInTimber")
    private val mLoggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        val contentType = response.body?.contentType()
        val content = response.body?.string()
        Timber.tag("wpg")
            .d("request url:" + request.url + "\ntime:" + (t2 - t1) / 1e6 + "\nbody:" + content + "\n")
        response.newBuilder()
            .body(content?.toResponseBody(contentType))
            .build()
    }

    fun <S> getService(service: Class<S>, hostType: Int): S {
        val build: Retrofit.Builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getHost(hostType))
        retrofitBuilder(build)
        return build.build().create(service)
    }

    abstract fun retrofitBuilder(build: Retrofit.Builder)

    abstract fun handleBuilder(builder: OkHttpClient.Builder)
}