package com.wpg.coroutine.app

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/17 17:18
 * @Description:
 */
@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val requestOptions = RequestOptions()
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val format =
            if (activityManager.isLowRamDevice) DecodeFormat.PREFER_RGB_565 else DecodeFormat.PREFER_ARGB_8888
        requestOptions.swap(format, builder)
    }

    //扩展函数
    private fun RequestOptions.swap(format: DecodeFormat, builder: GlideBuilder) {
        this.run {
            format(format)
            disallowHardwareConfig()
        }
        builder.setDefaultRequestOptions(this)
    }
}