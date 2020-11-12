package com.chencc.androidstudynotescode.materialdesign.nestedscroll.tradition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.view_dispatch.RecyclerViewFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_nested_tradition.*


/**
 * 传统嵌套滑动
 */
class NestedTraditionActivity : AppCompatActivity(){
    val titles = listOf("第一页", "第二页", "第三页")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_nested_tradition_test)
        setContentView(R.layout.activity_nested_tradition)

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = ViewPager2Adapter(this@NestedTraditionActivity ,initRecyclerViewData())

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = titles[position]
        }).attach()


    }


    private fun initRecyclerViewData(): MutableList<Fragment> {
        return mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }
}