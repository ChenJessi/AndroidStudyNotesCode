package com.chencc.androidstudynotescode.utils.blockcanary

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Printer


private const val TAG = "LogMonitor"
class LogMonitor : Printer{

    // 采样频率
    private var mSampleInterval = 1000L
    private var mPrintingStarted = false
    private var mStackSampler : StackSampler

    private var mStartTimestamp = 0L
    private val mBlockThresholdMillis = 3000L

    private var mLogHandler : Handler
    init {
        mStackSampler = StackSampler(mSampleInterval)
        val handlerThread = HandlerThread("block-canary-io").apply {
            start()
        }

        mLogHandler = Handler(handlerThread.looper)
    }


    override fun println(x: String?) {
        // 从 if 到 else dispatchMessage  如果执行耗时超过阈值，输出卡顿信息
        if (!mPrintingStarted){
            // 记录开始时间
            mStartTimestamp = System.currentTimeMillis()
            mPrintingStarted = true
            mStackSampler.startDump()

        }else{
            val endTime = System.currentTimeMillis()
            mPrintingStarted = false
            if (isBlock(endTime)){
                notifyBlockEvent(endTime)
            }
            mStackSampler.stopDump()
        }

    }


    private fun notifyBlockEvent(endTime : Long){
        mLogHandler.post {
            // 获得卡顿时主线程堆栈
            mStackSampler.getStacks(mStartTimestamp, endTime).forEach {
                Log.e(TAG, "notifyBlockEvent:  $it" )
            }
        }
    }


    private fun isBlock(endTime : Long) =  endTime - mStartTimestamp > mBlockThresholdMillis
}