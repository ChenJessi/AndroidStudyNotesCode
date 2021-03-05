package com.chencc.androidstudynotescode.utils.battery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityBatteryBinding

class BatteryActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityBatteryBinding>(this, R.layout.activity_battery)
        /**
         * 添加白名单
         */
        binding.button1.setOnClickListener {
            addWhileList(this@BatteryActivity)
        }
    }
}