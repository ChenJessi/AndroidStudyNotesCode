package com.chencc.androidstudynotescode.nestedscroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_nested_viewpager.*


/**
 * perfect_nestedscroll_viewpager
 */
class NestedViewPagerActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_viewpager)
        viewPager.adapter = ViewPager2Adapter(this@NestedViewPagerActivity, initReacyclerViewData())
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