package com.chencc.androidstudynotescode.utils.blockcanary

import android.os.Looper

/**
 *
 */
object BlockCanary {

    fun install(){
        Looper.getMainLooper().setMessageLogging(LogMonitor())
    }
}