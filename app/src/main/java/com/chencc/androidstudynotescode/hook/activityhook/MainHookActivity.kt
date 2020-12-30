package com.chencc.androidstudynotescode.hook.activityhook

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityMainHookBinding

/**
 * @author Created by CHEN on 2020/12/30
 * @email 188669@163.com
 */
class MainHookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainHookBinding>(
            this,
            R.layout.activity_main_hook
        )
        binding.button1.setOnClickListener {  }
    }
}