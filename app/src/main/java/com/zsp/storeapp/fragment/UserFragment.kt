package com.zsp.storeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.FragUserBinding
import com.zsp.storeapp.vm.UserViewModel
import me.goldze.mvvmhabit.base.BaseFragment

/**
 * description:
 * author:created by Andy on 2020/12/15 0015 14:12
 * email:zsp872126510@gmail.com
 */
class UserFragment : BaseFragment<FragUserBinding, UserViewModel>() {
    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.frag_user
    }
}