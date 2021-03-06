package com.chencc.androidstudynotescode.utils.network

import android.content.Context
import android.net.TrafficStats
import android.util.Log

private const val TAG = "NetSpeed"

/**
 *  网速
 */
object NetSpeed {
    private var lastTotalRxBytes = 0L
    private var lastTimeStamp = 0L

    fun getNetSpeed(uid : Int) : String{
        val nowTotalRxBytes = getTotalRxBytes(uid)
        val nowTimeStamp = System.currentTimeMillis()
        val speed = (nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp )  // 毫秒转换
        Log.i(TAG, "getNetSpeed: ${(nowTotalRxBytes - lastTotalRxBytes) * 1000}")
        Log.i(TAG, "getNetSpeed: ${(nowTimeStamp - lastTimeStamp )}")
        Log.i(TAG, "getNetSpeed: nowTotalRxBytes : $nowTotalRxBytes  nowTimeStamp : $nowTimeStamp speed : $speed")
        lastTimeStamp = nowTimeStamp
        lastTotalRxBytes = nowTotalRxBytes
        return "$speed kb/s"
    }

    // getApplicationInfo().uid
    fun getTotalRxBytes(uid: Int) = if (TrafficStats.getUidRxBytes(uid).toInt() == TrafficStats.UNSUPPORTED) 0L else TrafficStats.getTotalRxBytes() / 1024  // 转为kb
}