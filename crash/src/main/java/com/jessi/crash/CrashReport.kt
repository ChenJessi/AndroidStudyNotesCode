package com.jessi.crash

import android.content.Context
import java.io.File

object CrashReport{

    init {
        System.loadLibrary("bugly")
    }
    fun init(context: Context){
        val applicationContext = context.applicationContext
        CrashHandler.init(applicationContext)

        val file = File(applicationContext.externalCacheDir, "native_crash")
        if (!file.exists()){
            file.mkdirs()
        }
        initNativeCrash(file.absolutePath)
    }


    external fun testNativeCrash()
    external fun initNativeCrash(path : String)

}