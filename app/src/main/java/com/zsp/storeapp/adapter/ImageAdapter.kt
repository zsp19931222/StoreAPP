package com.zsp.storeapp.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter
import com.zsp.storeapp.entity.BannerResult
import com.zsp.storeapp.glide.GlideLoadUtils


/**
 * description:
 * author:created by Andy on 2020/12/1 0001 15:03
 * email:zsp872126510@gmail.com
 */
 class ImageAdapter(mDatas: List<BannerResult>?) :BannerAdapter<BannerResult, ImageAdapter.BannerViewHolder?>(mDatas) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        return BannerViewHolder(imageView)
    }


    inner class BannerViewHolder(@NonNull view: ImageView) :
        RecyclerView.ViewHolder(view) {
        var imageView: ImageView

        init {
            imageView = view
        }
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerResult?,
        position: Int,
        size: Int
    ) {
        GlideLoadUtils.getInstance().glideLoad(holder?.imageView?.context,data?.bannerPath,holder?.imageView,0)
    }
}