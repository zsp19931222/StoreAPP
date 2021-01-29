package com.zsp.storeapp.fragment

//import com.zsp.storeapp.adapter.SportsNewsAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.SportsNewsAdapter
import com.zsp.storeapp.databinding.FragMainBinding
import com.zsp.storeapp.entity.SportsNewsEntity
import com.zsp.storeapp.util.StateUtil
import com.zsp.storeapp.vm.FragMainViewModel
import me.andy.mvvmhabit.base.BaseFragment
import me.andy.mvvmhabit.bus.RxBus
import me.andy.mvvmhabit.util.ZLog

/**
 * description:
 * author:created by Andy on 2020/12/29 0029 17:37
 * email:zsp872126510@gmail.com
 */
class FragMain(var state: Int) : BaseFragment<FragMainBinding, FragMainViewModel>() {
    var pageSize = 20
    var pageNum = 1
    var list = mutableListOf<SportsNewsEntity>()


    var adapter = SportsNewsAdapter(R.layout.item_sport_news, list)
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.frag_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        ZLog.d(this)
        viewModel.state.value = this.state
        viewModel.pageNum.value = pageNum
        viewModel.pageSize.value = pageSize
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            binding.srl.finishRefresh()
            binding.srl.finishLoadMore()
        }

        RxBus.getDefault().toObservable(StateUtil::class.java).subscribe {
            ZLog.d(it.position)
            if (this.state == it.position) {
                smoothMoveToPosition(binding.rv, 0)
            }
        }

        viewModel.sportsList.observe(this) {
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
            ZLog.d("setOnLoadMoreListener", viewModel.pageNum.value?.inc())
            viewModel.pageNum.value = viewModel.pageNum.value?.inc()
        }
    }

    //目标项是否在最后一个可见项之后
    private var mShouldScroll = false

    //记录目标项位置
    private var mToPosition = 0

    //目标项是否在最后一个可见项之后 private boolean mShouldScroll; //记录目标项位置 private int mToPosition;
    //滑动到指定位置
    private fun smoothMoveToPosition(
        mRecyclerView: RecyclerView,
        position: Int
    ) { // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem =
            mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position)
            mToPosition = position
            mShouldScroll = true
        }
    }
}