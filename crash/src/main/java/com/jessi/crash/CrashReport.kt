package com.jessi.crash

import android.content.Context

object CrashReport{

    fun init(context: Context){
        val applicationContext = context.applicationContext
        CrashHandler.init(applicationContext)
    }
}