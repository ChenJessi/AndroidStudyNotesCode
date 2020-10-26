package com.chencc.androidstudynotescode.materialdesign.viewpager2

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.nestedscroll.RecyclerViewFragment
import com.chencc.androidstudynotescode.utils.dp2px
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_viewpager2.*

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
            add(RecyclerViewFragment())
        }
    }

    val titles = listOf("第一页", "第二页", "第三页")

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

//        根据滚动距离设置不同的title
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

        //  tabLayout 相关
        tabLayout.setTabTextColors(Color.BLACK, Color.RED)
        tabLayout.setSelectedTabIndicatorColor(Color.RED)
        tabLayout.isTabIndicatorFullWidth = false
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_BOTTOM)
        tabLayout.isInlineLabel = false


        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy {
            tab, position ->
            tab.text = titles[position]
            tab.setIcon(R.mipmap.topmenu_icn_member)
        }).attach()

    }
}