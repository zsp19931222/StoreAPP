package com.zsp.storeapp.activity

import android.os.Bundle
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityWebBinding
import com.zsp.storeapp.vm.WebViewModel
import me.andy.mvvmhabit.base.BaseActivity

/**
 * description:
 * author:created by Andy on 2020/12/31 0031 11:14
 * email:zsp872126510@gmail.com
 */
class WebActivity : BaseActivity<ActivityWebBinding, WebViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_web
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        binding.wb.loadUrl(intent.extras?.getString("href").toString())
    }
}