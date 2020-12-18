package com.zsp.storeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.FragLoginBinding
import com.zsp.storeapp.vm.LoginViewModel
import me.goldze.mvvmhabit.base.BaseFragment

/**
 * description:
 * author:created by Andy on 2020/12/4 0004 15:57
 * email:zsp872126510@gmail.com
 */
class LoginFragment : BaseFragment<FragLoginBinding, LoginViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.frag_login
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}