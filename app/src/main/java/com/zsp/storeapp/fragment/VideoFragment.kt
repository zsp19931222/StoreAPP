package com.zsp.storeapp.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.VideoAdapter
import com.zsp.storeapp.databinding.ActivityVideoBinding
import com.zsp.storeapp.entity.SportsVideoEntity
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
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        viewModel.pageNum.value = pageNum
        viewModel.pageSize.value = pageSize
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter
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