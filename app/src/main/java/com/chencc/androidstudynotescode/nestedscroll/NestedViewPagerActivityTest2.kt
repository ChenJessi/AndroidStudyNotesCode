package com.chencc.androidstudynotescode.nestedscroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_nested_viewpager_test1.*
import kotlinx.android.synthetic.main.activity_nested_viewpager_test2.*
import kotlinx.android.synthetic.main.activity_nested_viewpager_test2.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_nested_viewpager_test2.tabLayout
import kotlinx.android.synthetic.main.activity_nested_viewpager_test2.viewPager

/**
 * @author Created by CHEN on 2020/9/7
 * @email 188669@163.com
 *
 *   nestedscrollview_recyclerview
 */
class NestedViewPagerActivityTest2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_viewpager_test2)
        viewPager.adapter = ViewPager2Adapter(this@NestedViewPagerActivityTest2, initReacyclerViewData())
        val labels = arrayOf("linear", "scroll", "recycler")
        TabLayoutMediator(tabLayout, viewPager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = labels[position];
            }
        }).attach()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.isRefreshing = false
        }, 1000)
        }
    }


    private fun initReacyclerViewData() : MutableList<Fragment>{
        return mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }
}