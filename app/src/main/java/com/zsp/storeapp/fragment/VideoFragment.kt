package com.zsp.storeapp.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.VideoAdapter
import com.zsp.storeapp.databinding.ActivityVideoBinding
import com.zsp.storeapp.entity.SportsVideoEntity
import com.zsp.storeapp.viewpager.OnViewPagerListener
import com.zsp.storeapp.viewpager.ViewPagerLayoutManager
import com.zsp.storeapp.vm.VideoViewModel
import me.andy.mvvmhabit.base.BaseFragment
import me.andy.mvvmhabit.bus.RxBus
import me.andy.mvvmhabit.util.ZLog


/**
 * description:
 * author:created by Andy on 2021/1/8 0008 10:45
 * email:zsp872126510@gmail.com
 */
class VideoFragment : BaseFragment<ActivityVideoBinding, VideoViewModel>() {
    var list = mutableListOf<SportsVideoEntity>()
    var pageSize = 20
    var pageNum = 1
    var adapter = VideoAdapter(R.layout.item_video, list)
    private var mLayoutManager: ViewPagerLayoutManager? = null

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.activity_video
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("CheckResult")
    override fun initData() {
        binding.rv.setOnScrollChangeListener(object : RecyclerView.OnScrollListener(),
            View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                val rv: RecyclerView = v as RecyclerView
                val layoutManager = rv.layoutManager
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager is LinearLayoutManager) {
                    //获取最后一个可见view的位置
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    //获取第一个可见view的位置
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    ZLog.d(GSYVideoManager.instance().playPosition)
                    //大于0说明有播放
                    if (GSYVideoManager.instance().playPosition >= 0) {
                        //当前播放的位置
                        val position = GSYVideoManager.instance().playPosition
                        //对应的播放列表TAG
                        if (GSYVideoManager.instance().playTag == list[position].href && (position < firstVisibleItem || position > lastVisibleItem)
                        ) {
                            if (GSYVideoManager.isFullState(activity)) {
                                return
                            }
                            //如果滑出去了上面和下面就是否，和今日头条一样
                            GSYVideoManager.releaseAllVideos()
                            //防止出现Cannot call this method in a scroll callback
                            binding.rv.post { adapter.notifyDataSetChanged() }
                        }
                    }
                }
            }
        })

        viewModel.pageNum.value = pageNum
        viewModel.pageSize.value = pageSize
//        mLayoutManager = ViewPagerLayoutManager(activity, OrientationHelper.VERTICAL)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
//        initListener()
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            binding.srl.finishRefresh()
            binding.srl.finishLoadMore()
        }

        viewModel.videoList.observe(this) {
            list.addAll(it)
            adapter.notifyDataSetChanged()
            binding.srl.finishRefresh()
            binding.srl.finishLoadMore()
        }

        viewModel.pageNum.observe(this) {
            viewModel.getData()
        }

        binding.srl.setOnRefreshListener {
            list.clear()
            viewModel.pageNum.value = 1
        }
        binding.srl.setOnLoadMoreListener {
            viewModel.pageNum.value = viewModel.pageNum.value?.inc()
        }
    }

    private fun initListener() {
        mLayoutManager!!.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                ZLog.e(
                    "onInitComplete"
                )
                playVideo(0)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                ZLog.e(
                    "释放位置:$position 下一页:$isNext"
                )
                var index = 0
                index = if (isNext) {
                    0
                } else {
                    1
                }
                releaseVideo(index)
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                ZLog.e(
                    "选中位置:$position  是否是滑动到底部:$isBottom"
                )
                playVideo(position)
            }
        })
    }

    private fun playVideo(position: Int) {
        val itemView: View = binding.rv.getChildAt(0)
        val videoView = itemView.findViewById<VideoView>(R.id.video_view)
        val imgPlay =
            itemView.findViewById<ImageView>(R.id.img_play)
        val imgThumb =
            itemView.findViewById<ImageView>(R.id.img_thumb)
        val rootView = itemView.findViewById<RelativeLayout>(R.id.root_view)
        val mediaPlayer = arrayOfNulls<MediaPlayer>(1)
        videoView.start()
        videoView.setOnInfoListener { mp, what, extra ->
            mediaPlayer[0] = mp
            mp.isLooping = true
            imgThumb.animate().alpha(0f).setDuration(200).start()
            false
        }
        videoView.setOnPreparedListener { }
        imgPlay.setOnClickListener(object : View.OnClickListener {
            var isPlaying = true
            override fun onClick(v: View) {
                isPlaying = if (videoView.isPlaying) {
                    imgPlay.animate().alpha(1f).start()
                    videoView.pause()
                    false
                } else {
                    imgPlay.animate().alpha(0f).start()
                    videoView.start()
                    true
                }
            }
        })
    }


    private fun releaseVideo(index: Int) {
        val itemView: View = binding.rv.getChildAt(index)
        val videoView = itemView.findViewById<VideoView>(R.id.video_view)
        val imgThumb =
            itemView.findViewById<ImageView>(R.id.img_thumb)
        val imgPlay =
            itemView.findViewById<ImageView>(R.id.img_play)
        videoView.stopPlayback()
        imgThumb.animate().alpha(1f).start()
        imgPlay.animate().alpha(0f).start()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}