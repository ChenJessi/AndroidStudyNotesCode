package com.chencc.androidstudynotescode.customview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.customview.decoration.DecorationActivity
import com.chencc.androidstudynotescode.customview.fishview.FishActivity
import com.chencc.androidstudynotescode.customview.flowLayout.TestFlowActivity
import com.chencc.androidstudynotescode.customview.layoutManager.SlideCardActivity
import com.chencc.androidstudynotescode.customview.photoView.MultiTouchEventActivity
import com.chencc.androidstudynotescode.customview.photoView.PhotoViewActivity
import com.chencc.androidstudynotescode.customview.viewpager.TestViewPagerActivity
import kotlinx.android.synthetic.main.activity_customview.*

/**
 * ×Ô¶¨ÒåView Á·Ï°
 *
 */
class CustomViewActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customview)

        text1.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, TestFlowActivity::class.java))
        }
        text2.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, TestViewPagerActivity::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, FishActivity::class.java))
        }
        button4.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, DecorationActivity::class.java))
        }
        button5.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, SlideCardActivity::class.java))
        }
        button6.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, PhotoViewActivity::class.java))
        }
        button7.setOnClickListener {
            startActivity(Intent(this@CustomViewActivity, MultiTouchEventActivity::class.java))
        }
    }
}