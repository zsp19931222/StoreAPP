package com.zsp.storeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.FragmentSortBinding
import com.zsp.storeapp.vm.SortViewModel
import me.goldze.mvvmhabit.base.BaseFragment

/**
 * description:
 * author:created by Andy on 2020/12/3 0003 15:18
 * email:zsp872126510@gmail.com
 */
class SortFragment : BaseFragment<FragmentSortBinding, SortViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_sort
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        binding.rv1.layoutManager = LinearLayoutManager(activity)
        binding.rv1.adapter = viewModel.categoryAdapter
        viewModel.getCategoryList()
        viewModel.categoryAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                viewModel.getProductList(viewModel.categoryList[position].id)
            }


        binding.rv2.layoutManager = LinearLayoutManager(activity)
        binding.rv2.adapter = viewModel.productAdapter
    }
}