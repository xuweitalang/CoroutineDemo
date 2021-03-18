package com.wpg.coroutine.utils

import android.content.Context
import android.widget.ImageView
import com.wpg.coroutine.R
import com.wpg.coroutine.app.GlideApp

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/17 17:01
 * @Description:
 */
class GlideImageLoader {
    fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideApp.with(context).load(path).centerCrop().placeholder(R.drawable.ic_img_code)
            .error(R.drawable.ic_img_code)
            .into(imageView)
    }
}