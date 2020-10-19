package com.chencc.androidstudynotescode.lazyfragment

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_lazy_fragment1.*

class LazyFragment1Activity : AppCompatActivity() {

    private val mFragment1 by lazy { mutableListOf<Fragment>().apply {
        add(MyFragment1.newInstance(1))
        add(MyFragment1.newInstance(2))
        add(MyFragment1.newInstance(3))
        add(MyFragment1.newInstance(4))
        add(MyFragment1.newInstance(5))
    } }
    private val adapter by lazy { ViewPagerAdapter(supportFragmentManager, mFragment1) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lazy_fragment1)

        val type = intent.getIntExtra(type, 1)

        navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_1 -> {
                    viewPager.setCurrentItem(0 , true)
                    true
                }
                R.id.navigation_2 -> {
                    viewPager.setCurrentItem(1 , true)
                    true
                }
                R.id.navigation_3 -> {
                    viewPager.setCurrentItem(2 , true)
                    true
                }
                R.id.navigation_4 -> {
                    viewPager.setCurrentItem(3 , true)
                    true
                }
                R.id.navigation_5 -> {
                    viewPager.setCurrentItem(4 , true)
                    true
                }
                else -> {
                    false
                }
            }

        }

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                navView.selectedItemId = position
            }
        })
    }
}