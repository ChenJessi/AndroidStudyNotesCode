package com.chencc.androidstudynotescode.nestedscroll

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_nested_viewpager_test1.*
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

/**
 * @author Created by CHEN on 2020/9/4
 * @email 188669@163.com
 */
class NestedViewPagerActivityTest1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_viewpager_test1)
        viewPager.adapter = ViewPagerAdapter(this@NestedViewPagerActivityTest1, initRecyclerViewFraments())
        val labels = arrayOf("linear", "scroll", "recycler")
        TabLayoutMediator(tabLayout , viewPager, object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = labels[position]
            }
        }).attach()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.setRefreshing(false);
                }, 1000)
        }
    }


    private fun initRecyclerViewFraments() : MutableList<Fragment>{
        return  mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }
}