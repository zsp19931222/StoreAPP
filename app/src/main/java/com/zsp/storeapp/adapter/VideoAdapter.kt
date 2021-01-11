package com.zsp.storeapp.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zsp.storeapp.R
import com.zsp.storeapp.entity.SportsVideoEntity
import me.andy.mvvmhabit.util.ZLog

/**
 * description:
 * author:created by Andy on 2021/1/8 0008 11:14
 * email:zsp872126510@gmail.com
 */
class VideoAdapter(
    layoutResId: Int,
    data: List<SportsVideoEntity>
) : BaseQuickAdapter<SportsVideoEntity, BaseViewHolder>(
    layoutResId,
    data as MutableList<SportsVideoEntity>
) {
    @SuppressLint("CheckResult")
    override fun convert(holder: BaseViewHolder, item: SportsVideoEntity) {
        holder.setText(R.id.v_title, item.title)
        val gsyVideoPlayer = holder.getView<StandardGSYVideoPlayer>(R.id.detail_player)
        val img = ImageView(holder.itemView.context)
        Glide.with(holder.itemView.context).asBitmap().load("http:"+item.img).addListener(object : RequestListener<Bitmap?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap?>,
                    isFirstResource: Boolean
                ): Boolean {
                    ZLog.d(e)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any,
                    target: Target<Bitmap?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    ZLog.d(resource)
                    img.setImageBitmap(resource)
                    return false
                }
            }).into(img)

        gsyVideoPlayer.thumbImageView = img

        gsyVideoPlayer.setUpLazy(item.url, true, null, null, "")

        //增加title
        gsyVideoPlayer.titleTextView.visibility = View.GONE
        //设置返回键
        gsyVideoPlayer.backButton.visibility = View.GONE

        //设置全屏按键功能
        gsyVideoPlayer.fullscreenButton.setOnClickListener {
            gsyVideoPlayer.startWindowFullscreen(
                context,
                false,
                true
            )
        }
        //防止错位设置
        gsyVideoPlayer.playTag = item.href
        gsyVideoPlayer.playPosition = holder.adapterPosition
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        gsyVideoPlayer.isAutoFullWithSize = true
        //音频焦点冲突时是否释放
        gsyVideoPlayer.isReleaseWhenLossAudio = false
        //全屏动画
        gsyVideoPlayer.isShowFullAnimation = true
        //小屏时不触摸滑动
        gsyVideoPlayer.setIsTouchWiget(false)
    }
}