package com.wpg.coroutine.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wpg.coroutine.data.bean.Banner
import com.wpg.coroutine.utils.GlideImageLoader
import com.youth.banner.adapter.BannerAdapter

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/17 16:35
 * @Description:
 */
class ImageAdapter(mData: List<Banner>?, val context: Context) :
    BannerAdapter<Banner, ImageAdapter.ImageHolder>(mData) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = param
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder?, data: Banner?, position: Int, size: Int) {
        GlideImageLoader().displayImage(context, data!!.imagePath, holder!!.imageView)
    }

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }
}