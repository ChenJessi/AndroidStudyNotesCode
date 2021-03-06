package com.chencc.androidstudynotescode.utils.battery

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.work.*


/**
 * 电量优化相关
 */
private const val TAG = "BatteryUtils"

/**
 *添加白名单
 */
fun addWhileList(context : Context) {
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        // 检测是否处于白名单
        if (!pm.isIgnoringBatteryOptimizations(context.packageName)){
            Log.i(TAG, "addWhileList: 添加白名单")
            //直接询问用户是否允许把我们应用加入到白名单
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
                context.startActivity(this)
            }

            // 跳转到电量优化管理设置
//            context.startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }else{
            Log.i(TAG, "应用 ${context.packageName} 已经在白名单")
        }
    }
}

/**
 * 鸡肋
 * 唤醒 CPU
 */
fun wakeLock(context: Context) : PowerManager.WakeLock? {
    // 跨进程获取 powerManager 服务
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    // 判断是否支持 CPU 唤醒
    val isWakeLockLevelSupported = pm.isWakeLockLevelSupported(PowerManager.PARTIAL_WAKE_LOCK)

    Log.i(TAG, "是否支持 wakeLock: $isWakeLockLevelSupported")

    var wakeLock : PowerManager.WakeLock? = null
    // 支持 CPU 唤醒， 才保持唤醒
    if (isWakeLockLevelSupported){
        // 创建只唤醒 CPU 的唤醒锁
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "example:WAKE_LOCK")
//        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP , "example:WAKE_LOCK")
        // 开始唤醒 CPU
        wakeLock.acquire()
    }

    return wakeLock
}


/**
 * 后台任务
 */
fun doWork(context: Context){

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)      //Wi-Fi
        .setRequiresCharging(true)      // 在设备充电时运行
        .setRequiresBatteryNotLow(true) // 电量不足不会运行
        .build()

    val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork("upload", ExistingWorkPolicy.KEEP, uploadWorkRequest)

}


fun register(context: Context) : PowerConnectionReceiver{
    val filter = IntentFilter().apply {
        // 充电状态
        addAction(ACTION_POWER_CONNECTED)
        addAction(ACTION_POWER_DISCONNECTED)
        // 电量显著变回
        addAction(ACTION_BATTERY_LOW)
        addAction(ACTION_BATTERY_OKAY)
    }
    return PowerConnectionReceiver().apply {
        context.registerReceiver(this, filter)
    }
}



fun checkBattery(context: Context){
    val filter = IntentFilter(ACTION_BATTERY_CHANGED)
    val batteryStatus = context.registerReceiver(null, filter)

    // 是否正在充电
    val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

    //  什么方式充电
    val chargePlug = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1

    // USB
    val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
    // 充电器
    val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

    // 获得电量
    // 电量百分比
    val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

    val batteryPct = level * 100 / scale.toFloat()

    Log.i(TAG, "checkBattery:   isCharging : $isCharging   usbCharge : $usbCharge  acCharge : $acCharge")
    Log.i(TAG, "checkBattery:  当前电量 : $batteryPct  level : $level  scale : $scale")

}




