package com.chencc.androidstudynotescode.materialdesign.viewpager2

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.nestedscroll.RecyclerViewFragment
import com.google.android.material.appbar.AppBarLayout
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_viewpager2.*
import kotlin.math.abs

/**
 * @author Created by CHEN on 2020/10/25
 * @email 188669@163.com
 * ViewPager2 练习
 */
class ViewPager2Activity : AppCompatActivity(){

    val mFragments by lazy {
        mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setContentView(R.layout.activity_viewpager2)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar.layoutParams?.apply {
            this as FrameLayout.LayoutParams
            this.setMargins(0,QMUIStatusBarHelper.getStatusbarHeight(this@ViewPager2Activity),0,0)
        }

        collapsingToolbarLayout.title = "哆啦a梦"
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)


//        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
//            appBarLayout, verticalOffset ->
//            when(abs(verticalOffset) >= appBarLayout.totalScrollRange){
//                true ->{
//                    collapsingToolbarLayout.title = "哆啦A梦"
//                }
//                false ->{
//                    collapsingToolbarLayout.title = ""
//                }
//            }
//        })

        viewPager2.adapter = ViewPager2Adapter(this@ViewPager2Activity, mFragments)
    }
}