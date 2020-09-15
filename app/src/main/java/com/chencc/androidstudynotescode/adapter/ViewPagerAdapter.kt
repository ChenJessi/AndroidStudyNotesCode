package com.chencc.androidstudynotescode.adapter

import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class ViewPager2Adapter(var activity: FragmentActivity, var mList : MutableList<Fragment>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = mList.size


    override fun createFragment(position: Int): Fragment {
        return mList[position]
    }
}

class ViewPagerAdapter(var manager: FragmentManager, var mList : MutableList<Fragment>) : FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int = mList.size

}