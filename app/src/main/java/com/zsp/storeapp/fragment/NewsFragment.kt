package com.zsp.storeapp.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityNewsBinding
import com.zsp.storeapp.push.ISetAlias
import com.zsp.storeapp.push.SetAliasUtil
import com.zsp.storeapp.util.StateUtil
import com.zsp.storeapp.vm.NewsViewModel
import me.andy.mvvmhabit.base.BaseFragment
import me.andy.mvvmhabit.bus.RxBus
import me.andy.mvvmhabit.util.ZLog

/**
 * description:
 * author:created by Andy on 2021/1/11 0011 10:07
 * email:zsp872126510@gmail.com
 */
class NewsFragment : BaseFragment<ActivityNewsBinding, NewsViewModel>() {
    val tabs = arrayOf("国际足球", "国内足球", "NBA", "CBA", "综合体育", "最新新闻", "虎扑足球")
    private var mediator: TabLayoutMediator? = null
    private var activeColor = Color.parseColor("#ff678f")
    private var normalColor = Color.parseColor("#666666")
    var index: Int = 0

    private var activeSize = 20
    private var normalSize = 20
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.activity_news
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        //预加载
        binding.viewPager.offscreenPageLimit = 3
        //Adapter
        binding.viewPager.adapter =
            object : FragmentStateAdapter(activity?.supportFragmentManager!!, lifecycle) {
                override fun getItemCount(): Int {
                    return tabs.size
                }

                override fun createFragment(position: Int): Fragment {
                    return FragMain(position)
                }
            }


        mediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                //这里可以自定义TabView
                val states = arrayOfNulls<IntArray>(2)
                states[0] = intArrayOf(android.R.attr.state_selected)
                states[1] = intArrayOf()
                val colors = intArrayOf(activeColor, normalColor)
                val colorStateList = ColorStateList(states, colors)

                val tabView = TextView(activity)
                tabView.text = tabs[position]
                tabView.textSize = normalSize.toFloat()
                tabView.setTextColor(colorStateList)
                tabView.gravity = Gravity.CENTER

                tab.customView = tabView
            })
        //要执行这一句才是真正将两者绑定起来
        mediator?.attach()

        //viewPager 页面切换监听
        binding.viewPager.registerOnPageChangeCallback(changeCallback)

        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if ("news返回顶部" == it)
                ZLog.d(index)
                RxBus.getDefault().post(StateUtil(index))
        }
    }

    override fun onDestroy() {
        mediator?.detach()
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback)
        super.onDestroy()
    }

    private var changeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            index = position
            //可以来设置选中时tab的大小
            val tabCount = binding.tabLayout.tabCount
            for (i in 0 until tabCount) {
                val tab: TabLayout.Tab? = binding.tabLayout.getTabAt(i)
                val tabView: TextView = tab?.customView as TextView
                if (tab.position == position) {
                    tabView.textSize = activeSize.toFloat()
                    tabView.typeface = Typeface.DEFAULT_BOLD
                } else {
                    tabView.textSize = normalSize.toFloat()
                    tabView.typeface = Typeface.DEFAULT
                }
            }
        }
    }
}