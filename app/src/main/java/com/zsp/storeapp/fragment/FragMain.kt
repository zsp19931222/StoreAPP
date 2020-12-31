package com.zsp.storeapp.fragment

//import com.zsp.storeapp.adapter.SportsNewsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.adapter.SportsNewsAdapter
import com.zsp.storeapp.databinding.FragMainBinding
import com.zsp.storeapp.entity.SportsNewsEntity
import com.zsp.storeapp.util.IsNullUtil
import com.zsp.storeapp.vm.FragMainViewModel
import me.andy.mvvmhabit.base.BaseFragment
import me.andy.mvvmhabit.util.ZLog
import java.util.*

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

    override fun initData() {
        ZLog.d(this)
        viewModel.state.value = this.state
        viewModel.pageNum.value = pageNum
        viewModel.pageSize.value = pageSize
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
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
            ZLog.d("setOnLoadMoreListener",viewModel.pageNum.value?.inc())
            viewModel.pageNum.value = viewModel.pageNum.value?.inc()
        }
    }
}