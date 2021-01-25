package com.chencc.androidstudynotescode.materialdesign.viewpager2

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.nestedscroll.RecyclerViewFragment
import com.chencc.androidstudynotescode.utils.dp2px
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_snackbar.*
import kotlinx.android.synthetic.main.activity_viewpager2.*

/**
 * @author Created by CHEN on 2020/10/25
 * @email 188669@163.com
 * ViewPager2 练习
 */

private const val TAG = "ViewPager2Activity"

class ViewPager2Activity : AppCompatActivity() {

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
            this.setMargins(0, QMUIStatusBarHelper.getStatusbarHeight(this@ViewPager2Activity), 0, 0)
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
        tabLayout.tabIconTint = ContextCompat.getColorStateList(this@ViewPager2Activity, R.color.tablayout_icon_color_list)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.badge?.number = tab?.badge?.number ?: 0 + 1
                Log.e(TAG, "onTabUnselected:  ${tab?.position}" )
                val number = tab?.badge?.number?:0
                tab?.badge?.clearNumber()
                tab?.badge?.number = number + 1
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.e(TAG, "onTabSelected:  ${tab?.position}" )
                val number = tab?.badge?.number?:0
                tab?.badge?.clearNumber()
                tab?.badge?.number = number + 1
            }
        })
        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.orCreateBadge.apply {
                badgeTextColor = Color.WHITE
                backgroundColor = ContextCompat.getColor(this@ViewPager2Activity, R.color.color_bg)
                // number 最大长度 3  超过三位用 + 符号代替  例如：最大为99+
                maxCharacterCount = 3
                // number 不为空时 返回 number 或者最大数量
                // number为空时  返回此提示
                tab.badge?.setContentDescriptionNumberless("number 值为空")
                // 数字角标
                number = 80
            }

            tab.text = titles[position]
            tab.setIcon(R.mipmap.topmenu_icn_member)
            Log.e(TAG, "TabLayoutMediator  position:  $position ")
        }).attach()

    }
}