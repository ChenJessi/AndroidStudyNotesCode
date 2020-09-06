package com.chencc.androidstudynotescode.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class ViewPagerAdapter(var activity: FragmentActivity, var mList : MutableList<Fragment>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = mList.size


    override fun createFragment(position: Int): Fragment {
        return mList[position]
    }
}