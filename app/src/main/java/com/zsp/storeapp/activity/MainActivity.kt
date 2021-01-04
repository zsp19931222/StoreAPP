package com.zsp.storeapp.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.zsp.storeapp.BR
import com.zsp.storeapp.R
import com.zsp.storeapp.databinding.ActivityMainBinding
import com.zsp.storeapp.fragment.FragMain
import com.zsp.storeapp.push.ISetAlias
import com.zsp.storeapp.push.SetAliasUtil
import com.zsp.storeapp.vm.MainViewModel
import me.andy.mvvmhabit.base.BaseActivity

/**
 * description:
 * author:created by Andy on 2020/12/29 0029 16:26
 * email:zsp872126510@gmail.com
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val tabs = arrayOf("国际足球", "国内足球", "NBA","CBA", "综合体育", "最新新闻","虎扑足球")
    private var mediator: TabLayoutMediator? = null
    private var activeColor = Color.parseColor("#ff678f")
    private var normalColor = Color.parseColor("#666666")

    private var activeSize = 20
    private var normalSize = 20


    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val set: ISetAlias = SetAliasUtil(this)
        set.setAlias("45145")
        //预加载
        binding.viewPager.offscreenPageLimit = 3
        //Adapter
        binding.viewPager.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
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
            TabConfigurationStrategy { tab, position ->
                //这里可以自定义TabView
                val states = arrayOfNulls<IntArray>(2)
                states[0] = intArrayOf(android.R.attr.state_selected)
                states[1] = intArrayOf()
                val colors = intArrayOf(activeColor, normalColor)
                val colorStateList = ColorStateList(states, colors)

                val tabView = TextView(this)
                tabView.text = tabs[position]
                tabView.textSize= normalSize.toFloat()
                tabView.setTextColor(colorStateList)
                tabView.gravity=Gravity.CENTER

                tab.customView = tabView
            })
        //要执行这一句才是真正将两者绑定起来
        mediator?.attach()

        //viewPager 页面切换监听
        binding.viewPager.registerOnPageChangeCallback(changeCallback)
    }

    override fun onDestroy() {
        mediator?.detach()
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback)
        super.onDestroy()
    }

    private var changeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
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