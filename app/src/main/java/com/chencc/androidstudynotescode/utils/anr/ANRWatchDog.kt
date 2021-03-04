package com.chencc.androidstudynotescode.utils.anr

import android.os.*
import android.util.Log
import java.lang.StringBuilder


private const val TAG = "ANRWatchDog"

class ANRWatchDog private constructor(): Thread() {
    private val timeout = 5000L
    private var ignoreDebugger = true

    private val mainHandler = Handler(Looper.getMainLooper())

    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {  ANRWatchDog() }
    }


    private inner class ANRChecker : Runnable{

        private var mCompleted = false
        private var mStartTime = 0L
        private var executeTime = SystemClock.uptimeMillis()

        override fun run() {
            synchronized(this@ANRWatchDog) {
                mCompleted = true
                executeTime = SystemClock.uptimeMillis()
            }
        }

        fun schedule(){
            mCompleted = false
            mStartTime = SystemClock.uptimeMillis()
            mainHandler.postAtFrontOfQueue(this)

        }

        fun isBlocked() =  !mCompleted || executeTime - mStartTime >= 5000;
    }


    private var onAnrHappened : ((stackTraceInfo : String) -> Unit)? = null

    private val anrChecker = ANRChecker()

    fun addANRListener(onAnrHappened : (String)->Unit){
        this.onAnrHappened = onAnrHappened
    }

    override fun run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)   // 设置为后台线程
        while (true){
             loop@ while (!isInterrupted){
                   synchronized(this){
                        anrChecker.schedule()
                        var waitTime = timeout
                        var start = SystemClock.uptimeMillis()
                        while (waitTime > 0){
                            try {
                                sleep(waitTime.toLong())
//                            wait(waitTime.toLong())
                            } catch (e: InterruptedException) {
                                Log.w(TAG, e.toString())
                            }
                            waitTime = timeout - (SystemClock.uptimeMillis() - start)
                        }
                        if (!anrChecker.isBlocked()){
                            return@synchronized
                        }
                    }
                 if (!anrChecker.isBlocked()){
                     continue@loop
                 }
                 if (!ignoreDebugger && Debug.isDebuggerConnected()){
                     continue@loop
                 }
                 val stackTraceInfo = getStackTraceInfo()
                 onAnrHappened?.invoke(stackTraceInfo)
             }
            onAnrHappened = null
        }
    }



    private fun getStackTraceInfo() : String{
        return StringBuilder().apply {
            Looper.getMainLooper().thread.stackTrace.forEach {
                append(it.toString())
                append("\r\n")
            }
        }.toString()
    }

}

