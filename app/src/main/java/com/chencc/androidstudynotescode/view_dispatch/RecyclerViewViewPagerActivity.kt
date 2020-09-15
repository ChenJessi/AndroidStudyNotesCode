package com.chencc.androidstudynotescode.view_dispatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_viewpager_recyclerview_dispatch.*


/**
 * recyclerView  viewpager ª¨∂Ø≥ÂÕª¥¶¿Ì
 */
class RecyclerViewViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_recyclerview_dispatch)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, initReacyclerViewData())

    }

    private fun initReacyclerViewData() : MutableList<Fragment>{
        return mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }
}