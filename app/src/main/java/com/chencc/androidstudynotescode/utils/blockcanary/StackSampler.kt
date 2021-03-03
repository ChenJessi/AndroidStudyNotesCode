package com.chencc.androidstudynotescode.utils.blockcanary

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicBoolean


const val SEPARATOR = "\r\n"
val TIME_FORMATTER: SimpleDateFormat = SimpleDateFormat("MM-dd HH:mm:ss.SSS")

class StackSampler(val mSampleInterval : Long) {

    private val mStackMap = linkedMapOf<Long, String>()
    private val mMaxCount = 100
    private val mShouldSample = AtomicBoolean(false)
    private var mHandler : Handler

    init {
        val handlerThread = HandlerThread("block-canary-sampler").apply {
            start()
        }

        mHandler = Handler(handlerThread.looper)
    }


    /**
     * 开始采样 执行栈堆
     */
    fun startDump(){
        // 避免重复开始
        if (mShouldSample.get()){
            return
        }
        mShouldSample.set(true)

        mHandler.removeCallbacks(mRunnable)
        mHandler.postDelayed(mRunnable, mSampleInterval);
    }

    fun stopDump(){
        if (!mShouldSample.get()){
            return
        }
        mShouldSample.set(false)
        mHandler.removeCallbacks(mRunnable)
    }



    fun getStacks(startTime : Long, endTime : Long) = mutableListOf<String>().apply {
        synchronized(mStackMap){
            mStackMap.forEach {
                if (it.key in startTime until endTime){
                    add(TIME_FORMATTER.format(it.key) + SEPARATOR + SEPARATOR + it.value)
                }
            }
        }
    }


    private val mRunnable : Runnable = object : Runnable {
        override fun run() {
            val sb = StringBuilder()

            val stackTrace = Looper.getMainLooper().thread.stackTrace.forEach {
                sb.append(it.toString()).append("\n")
            }
            synchronized(mStackMap){
                // 最多保存100条堆栈信息
                if (mStackMap.size == mMaxCount){
                    mStackMap.remove(mStackMap.keys.iterator().next())
                }
                mStackMap[System.currentTimeMillis()] = sb.toString()
            }

            if (mShouldSample.get() ){
                mHandler.postDelayed(this, mSampleInterval)
            }
        }
    }

}