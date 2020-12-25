package com.zsp.storeapp.activity

import android.os.Bundle
import android.os.Looper
import androidx.annotation.Nullable
import androidx.lifecycle.observe
import com.luck.picture.lib.tools.ToastUtils
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityTestBinding
import com.zsp.storeapp.vm.TestViewModel
import io.reactivex.Observer
import me.andy.mvvmhabit.base.BaseActivity
import me.andy.mvvmhabit.util.ZLog


/**
 * description:
 * author:created by Andy on 2020/12/25 0025 11:44
 * email:zsp872126510@gmail.com
 */
class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_test
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.getBanner()
        viewModel.bannerData.observe(this) {
            ZLog.d("bannerData-------->",it)
        }
        viewModel.raidersData.observe(this) {
            ZLog.d("raidersData-------->",it)
        }
    }
}

