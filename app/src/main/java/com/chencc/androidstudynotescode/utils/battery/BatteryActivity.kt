package com.chencc.androidstudynotescode.utils.battery

import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityBatteryBinding
import com.chencc.androidstudynotescode.utils.network.NetSpeed
import com.jessi.arouter_annotation_java.ARouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "BatteryActivity"

/**
 * 电量优化
 */
@ARouter(path = "/optimization/ActivityResultTestActivity", group = "optimization")
class BatteryActivity : AppCompatActivity() , CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityBatteryBinding>(this, R.layout.activity_battery)
        /**
         * 添加白名单
         */
        binding.button1.setOnClickListener {
            addWhileList(this@BatteryActivity)
        }
        /**
         * 充电状态
         */
        binding.button2.setOnClickListener {
            checkBattery(this@BatteryActivity)
        }
        /**
         * 电源锁  鸡肋
         */
        binding.button3.setOnClickListener {
            wakeLock(this@BatteryActivity)
        }
        /**
         *  执行后台任务
         */
        binding.button4.setOnClickListener {
            doWork(this@BatteryActivity)
        }
        /**
         *  充电状态
         */
        binding.button5.setOnClickListener {
            register(this@BatteryActivity)
        }

        binding.button6.setOnClickListener {
            val speed = NetSpeed.getNetSpeed(applicationInfo.uid)
            binding.button6.text = speed
        }
    }
}