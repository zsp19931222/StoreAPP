package com.zsp.storeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.FragCartBinding
import com.zsp.storeapp.vm.CartViewModel
import me.goldze.mvvmhabit.base.BaseFragment

/**
 * description:
 * author:created by Andy on 2020/12/9 0009 16:59
 * email:zsp872126510@gmail.com
 */
class CartFragment : BaseFragment<FragCartBinding, CartViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.frag_cart
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        binding.rvContent.layoutManager=LinearLayoutManager(activity)
        binding.rvContent.adapter = viewModel.adapter
        viewModel.getData()
    }
}