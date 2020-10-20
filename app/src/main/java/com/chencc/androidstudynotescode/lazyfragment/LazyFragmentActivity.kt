package com.chencc.androidstudynotescode.lazyfragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_fragment_lazy.*

/**
 * fragment 懒加载
 * ViewPager ViewPager2
 */
const val FRAGMENT_TYPE = "type"
class LazyFragmentActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_lazy)
        button1.setOnClickListener {

            startActivity(Intent(this@LazyFragmentActivity, LazyFragment1Activity::class.java).apply {
                putExtra(FRAGMENT_TYPE, 1)
            })
        }
        button2.setOnClickListener {
            startActivity(Intent(this@LazyFragmentActivity, LazyFragment1Activity::class.java).apply {
                putExtra(FRAGMENT_TYPE, 2)
            })
        }
    }
}