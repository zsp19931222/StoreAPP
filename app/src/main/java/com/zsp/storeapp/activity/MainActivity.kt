package com.zsp.storeapp.activity

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.base.BaseViewModelKotlin
import com.zsp.storeapp.databinding.ActivityMainBinding
import com.zsp.storeapp.fragment.NewsFragment
import com.zsp.storeapp.fragment.VideoFragment
import com.zsp.storeapp.push.ISetAlias
import com.zsp.storeapp.push.SetAliasUtil
import me.andy.mvvmhabit.base.BaseActivity
import me.andy.mvvmhabit.util.ZLog

/**
 * description:
 * author:created by Andy on 2021/1/11 0011 09:58
 * email:zsp872126510@gmail.com
 */
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModelKotlin>() {
    private var newsFragment: NewsFragment? = null
    private var videoFragment: VideoFragment? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private var fragmentManager: FragmentManager? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val set: ISetAlias = SetAliasUtil(this)
        set.setAlias("45145")
        fragmentManager = supportFragmentManager
        initNews()
        binding.news.setOnClickListener { initNews() }
        binding.video.setOnClickListener { initVideo() }

    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    private fun initNews() {
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.let { hideFragment(it) }
            if (newsFragment == null) {
                newsFragment = NewsFragment()
                fragmentTransaction?.add(R.id.fl, newsFragment!!)
            } else {
                fragmentTransaction?.show(newsFragment!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
            ZLog.e(e.toString())
        }
    }

    private fun initVideo() {
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.let { hideFragment(it) }
            if (videoFragment == null) {
                videoFragment = VideoFragment()
                fragmentTransaction?.add(R.id.fl, videoFragment!!)
            } else {
                fragmentTransaction?.show(videoFragment!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
            ZLog.e(e.toString())
        }
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        if (newsFragment != null) {
            transaction.hide(newsFragment!!)
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment!!)
        }
    }
}