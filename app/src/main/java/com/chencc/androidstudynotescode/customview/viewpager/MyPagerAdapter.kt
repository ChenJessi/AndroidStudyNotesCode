package com.chencc.androidstudynotescode.customview.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.chencc.androidstudynotescode.R

class MyPagerAdapter(val mContext : Context, val mList : MutableList<Int>) : PagerAdapter() {


    override fun isViewFromObject(view: View, o : Any): Boolean {
        return view == o
    }

    override fun getCount(): Int  = Int.MAX_VALUE

    override fun instantiateItem(container: ViewGroup, p: Int): Any {
        val position = p % mList.size
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_test, container, false)
        val text = view.findViewById<TextView>(R.id.text)
        text.text = "$position"
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View?)
    }
}