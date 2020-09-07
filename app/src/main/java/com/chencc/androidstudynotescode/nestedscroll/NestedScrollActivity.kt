package com.chencc.androidstudynotescode.nestedscroll

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.skin.SkinTestActivity
import kotlinx.android.synthetic.main.activity_nested_scroll.*

/**
 * @author Created by CHEN on 2020/9/4
 * @email 188669@163.com
 * NestedScrollActivity
 */
class NestedScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)

        button1.setOnClickListener {
            startActivity(Intent(this@NestedScrollActivity, NestedViewPagerActivityTest1::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this@NestedScrollActivity, NestedViewPagerActivityTest2::class.java))
        }
        button3.setOnClickListener {

        }
        button4.setOnClickListener {

        }
    }
}