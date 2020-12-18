package com.zsp.storeapp.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityLoginRegisterBinding
import com.zsp.storeapp.fragment.LoginFragment
import com.zsp.storeapp.fragment.RegisterFragment
import com.zsp.storeapp.util.UIUtil
import com.zsp.storeapp.vm.LoginRegisterViewModel
import me.goldze.mvvmhabit.BR
import me.goldze.mvvmhabit.base.BaseActivity

/**
 * description:
 * author:created by Andy on 2020/12/4 0004 15:26
 * email:zsp872126510@gmail.com
 */
class LoginRegisterActivity : BaseActivity<ActivityLoginRegisterBinding, LoginRegisterViewModel>() {
    private val mFragments: MutableList<Fragment> = mutableListOf()

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login_register
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        initView()
        onViewClicked()
    }

    private fun initView() {
        mFragments.add(LoginFragment())
        mFragments.add(RegisterFragment())
        binding.viewPager.adapter = FragPagerAdapter(supportFragmentManager, mFragments)

        binding.viewPager.setCurrentItem(0, false)
        setTabUI(binding.tvLogin)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.viewPager.currentItem = 0
                    setTabUI(binding.tvLogin)
                } else if (position == 1) {
                    binding.viewPager.currentItem = 1
                    setTabUI(binding.tvRegister)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


    fun onViewClicked() {
        binding.tvLogin.setOnClickListener {
            binding.viewPager.currentItem =0
            setTabUI(binding.tvLogin)
        }
        binding.tvRegister.setOnClickListener {
            binding.viewPager.currentItem =1
            setTabUI(binding.tvRegister)
        }
    }

    private fun setTabUI(tvLogin: TextView) {
        binding.tvLogin.setTextColor(UIUtil.getColor(R.color.main))
        binding.tvRegister.setTextColor(UIUtil.getColor(R.color.main))
        binding.tvLogin.setBackgroundResource(R.drawable.round_rect_gray)
        binding.tvRegister.setBackgroundResource(R.drawable.round_rect_gray)
        tvLogin.setTextColor(UIUtil.getColor(R.color.white))
        tvLogin.setBackgroundResource(R.drawable.round_rect_main)
    }

    class FragPagerAdapter(fm: FragmentManager?, private val mFragments: List<Fragment>) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }
}