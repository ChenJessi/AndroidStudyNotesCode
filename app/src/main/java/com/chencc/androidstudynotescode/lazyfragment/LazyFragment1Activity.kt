package com.chencc.androidstudynotescode.lazyfragment

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
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
    private val mFragment2 by lazy { mutableListOf<Fragment>().apply {
        add(MyFragment2.newInstance(1))
        add(MyFragment2.newInstance(2))
        add(MyFragment2.newInstance(3))
        add(MyFragment2.newInstance(4))
        add(MyFragment2.newInstance(5))
    } }

    private val adapter by lazy { ViewPagerAdapter(supportFragmentManager, mFragment1) }
    private val adapter2 by lazy { ViewPager2Adapter(this@LazyFragment1Activity) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lazy_fragment1)

        val type = intent.getIntExtra(FRAGMENT_TYPE, 1)
        Log.e("TAG", "onCreate:    $type" )
        when(type){
            1 ->{
                viewPager2.visibility = GONE
                viewPager.visibility = VISIBLE
                vp1()
            }
            2 ->{
                viewPager.visibility = GONE
                viewPager2.visibility = VISIBLE
                vp2()
            }
        }
    }

    /**
     * viewPager1
     */
    private fun vp1(){
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

    /**
     * view
     */
    private fun vp2(){
        navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_1 -> {
                    viewPager2.setCurrentItem(0 , true)
                    true
                }
                R.id.navigation_2 -> {
                    viewPager2.setCurrentItem(1 , true)
                    true
                }
                R.id.navigation_3 -> {
                    viewPager2.setCurrentItem(2 , true)
                    true
                }
                R.id.navigation_4 -> {
                    viewPager2.setCurrentItem(3 , true)
                    true
                }
                R.id.navigation_5 -> {
                    viewPager2.setCurrentItem(4 , true)
                    true
                }
                else -> {
                    false
                }
            }

        }

        viewPager2.adapter = adapter2
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                navView.selectedItemId = position
            }
        })
    }
}


class ViewPager2Adapter(var activity : FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5


    override fun createFragment(position: Int): Fragment {
        return MyFragment2.newInstance(position)
    }
//    override fun createFragment(position: Int): Fragment {
//        return mList[position]
//    }
}
