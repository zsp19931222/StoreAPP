package com.zsp.storeapp.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zsp.storeapp.R
import com.zsp.storeapp.entity.SportsVideoEntity
import me.andy.mvvmhabit.glide.GlideLoadUtils


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
//        GlideLoadUtils.getInstance()
//            .glideLoad(holder.itemView.context, item.url, holder.getView(R.id.img_thumb), 0)
//        val videoView = holder.getView<FullScreenVideoView>(R.id.video_view)
//        videoView.setVideoURI(Uri.parse(item.url))
        holder.setText(R.id.v_title, item.title)
        val gsyVideoPlayer = holder.getView<StandardGSYVideoPlayer>(R.id.detail_player)
        val thumbImg = ImageView(holder.itemView.context)
        GlideLoadUtils.getInstance()
            .glideLoad(holder.itemView.context, "http:" + item.img, thumbImg, 0)
        gsyVideoPlayer.thumbImageViewLayout.visibility = View.VISIBLE
        gsyVideoPlayer.thumbImageView = thumbImg

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