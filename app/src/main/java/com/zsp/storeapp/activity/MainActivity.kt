package com.zsp.storeapp.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityMainBinding
import com.zsp.storeapp.fragment.CartFragment
import com.zsp.storeapp.fragment.HomeFragment
import com.zsp.storeapp.fragment.SortFragment
import com.zsp.storeapp.fragment.UserFragment
import com.zsp.storeapp.util.UIUtil
import com.zsp.storeapp.vm.MainViewModel
import me.goldze.mvvmhabit.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding?, MainViewModel>() {
    private var fragmentTransaction: FragmentTransaction? = null
    private var fragmentManager: FragmentManager? = null
    private var home: HomeFragment? = null
    private var sort: SortFragment? = null
    private var cart: CartFragment? = null
    private var user: UserFragment? = null

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        fragmentManager = supportFragmentManager
        initHome()
    }


    private fun initHome() {
        setTabUI(0)
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            hideFragment(fragmentTransaction!!)
            if (home == null) {
                home = HomeFragment()
                fragmentTransaction?.add(R.id.fl, home!!)
            } else {
                fragmentTransaction?.show(home!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
        }
    }

    private fun initSort() {
        setTabUI(1)
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            hideFragment(fragmentTransaction!!)
            if (sort == null) {
                sort = SortFragment()
                fragmentTransaction?.add(R.id.fl, sort!!)
            } else {
                fragmentTransaction?.show(sort!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
        }
    }

    private fun initCart() {
        setTabUI(2)
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            hideFragment(fragmentTransaction!!)
            if (cart == null) {
                cart = CartFragment()
                fragmentTransaction?.add(R.id.fl, cart!!)
            } else {
                fragmentTransaction?.show(cart!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
        }
    }

    private fun initUser() {
        setTabUI(3)
        try {
            fragmentTransaction = fragmentManager?.beginTransaction()
            hideFragment(fragmentTransaction!!)
            if (user == null) {
                user = UserFragment()
                fragmentTransaction?.add(R.id.fl, user!!)
            } else {
                fragmentTransaction?.show(user!!)
            }
            fragmentTransaction?.commit()
        } catch (e: Exception) {
        }
    }

    /**
     * description: 去除（隐藏）所有的Fragment
     * author: Andy
     * date: 2019/7/29 0029 10:22
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        if (home != null) {
            //隐藏方法
            transaction.hide(home!!)
        }
        if (sort != null) {
            transaction.hide(sort!!)
        }
        if (cart != null) {
            transaction.hide(cart!!)
        }
        if (user != null) {
            transaction.hide(user!!)
        }
    }

    private fun setTabUI(position: Int) {
        binding?.itHome?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.tvHome?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.itSort?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.tvSort?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.itCart?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.tvCart?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.itUser?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))
        binding?.tvUser?.setTextColor(UIUtil.getColor(R.color.tab_nor_color))

        when (position) {
            0 -> {
                binding?.itHome?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
                binding?.tvHome?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
            }
            1 -> {
                binding?.itSort?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
                binding?.tvSort?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
            }
            2 -> {
                binding?.itCart?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
                binding?.tvCart?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
            }
            3 -> {
                binding?.itUser?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
                binding?.tvUser?.setTextColor(UIUtil.getColor(R.color.tab_sel_color))
            }
        }
        binding?.llHome?.setOnClickListener { initHome() }
        binding?.llSoft?.setOnClickListener { initSort() }
        binding?.llCart?.setOnClickListener { initCart() }
        binding?.llUser?.setOnClickListener { initUser() }
    }

}