package com.zsp.storeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.FragmentHomeBinding
import com.zsp.storeapp.vm.HomeViewModel
import me.goldze.mvvmhabit.base.BaseFragment
import com.zsp.storeapp.BR

/**
 * description:
 * author:created by Andy on 2020/12/1 0001 14:23
 * email:zsp872126510@gmail.com
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(viewModel.imageAdapter).indicator = CircleIndicator(activity);
        viewModel?.getBannerList()

        binding.rv.layoutManager=LinearLayoutManager(activity)
        binding.rv.adapter=viewModel.productAdapter
        viewModel?.getProductList()

    }
}