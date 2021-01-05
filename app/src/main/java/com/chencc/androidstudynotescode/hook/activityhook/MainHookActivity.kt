package com.chencc.androidstudynotescode.hook.activityhook

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityMainHookBinding
import com.chencc.androidstudynotescode.hook.activityhook.hook.hookAMSAidl
import com.chencc.androidstudynotescode.hook.activityhook.hook.hookHandler
import com.chencc.androidstudynotescode.materialdesign.MaterialDesignActivity

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
        binding.button1.setOnClickListener {
            hookAMSAidl()
            hookHandler()
            val intent = Intent(this@MainHookActivity, TargetActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            val intent = Intent(this@MainHookActivity, MaterialDesignActivity::class.java)
            startActivity(intent)
        }
    }
}