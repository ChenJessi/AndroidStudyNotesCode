package com.chencc.androidstudynotescode.customview.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_view_pager_test.*

/**
 * ViewPager ≤‚ ‘
 */
class TestViewPagerActivity : AppCompatActivity(){

    val adapter : MyPagerAdapter by lazy {
        MyPagerAdapter(this@TestViewPagerActivity, mutableListOf(1,2,3,4,5))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_test)

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
    }
}