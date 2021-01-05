package com.chencc.androidstudynotescode.hook.activityhook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R

/**
 * @author Created by CHEN on 2020/12/30
 * @email 188669@163.com
 * 目标activity
 */
class TargetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
    }
}