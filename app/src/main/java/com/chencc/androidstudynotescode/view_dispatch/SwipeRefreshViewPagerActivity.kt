package com.chencc.androidstudynotescode.view_dispatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_viewpager_swiperefresh_dispatch.*


/**
 * SwipeRefreshLayout  viewpager ª¨∂Ø≥ÂÕª¥¶¿Ì
 */
class SwipeRefreshViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_swiperefresh_dispatch)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, initReacyclerViewData())

        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun initReacyclerViewData() : MutableList<Fragment>{
        return mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }
}