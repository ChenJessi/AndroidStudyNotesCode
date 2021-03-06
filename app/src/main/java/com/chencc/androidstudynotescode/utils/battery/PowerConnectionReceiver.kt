package com.chencc.androidstudynotescode.utils.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.widget.Toast

/**
 * 充电状态广播
 */
class PowerConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "充电状态 : 充电 CONNECTED", Toast.LENGTH_SHORT).show()
            }
            ACTION_POWER_DISCONNECTED -> {
                Toast.makeText(context, "充电状态 : 断开充电 DISCONNECTED", Toast.LENGTH_SHORT).show()
            }
            ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "电量过低", Toast.LENGTH_SHORT).show()
            }
            ACTION_BATTERY_OKAY -> {
                Toast.makeText(context, "电量从低变回高", Toast.LENGTH_SHORT).show()
            }
        }
    }
}