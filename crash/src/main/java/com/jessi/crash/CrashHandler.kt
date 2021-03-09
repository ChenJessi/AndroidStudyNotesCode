package com.jessi.crash

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "CrashHandler"
private const val FILE_NAME_SUFFIX = ".trace"

internal class CrashHandler private constructor() : Thread.UncaughtExceptionHandler{

    companion object{
        private var defaultUncaughtExceptionHandler : Thread.UncaughtExceptionHandler? = null
        private lateinit var context : Context

        fun init(applicationContext : Context) {
            context = applicationContext
            defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler())
        }
    }


    override fun uncaughtException(t: Thread, e: Throwable) {

        try {
            val file = dealException(t, e)
        } catch (e: Exception) {
        } finally {
            defaultUncaughtExceptionHandler?.uncaughtException(t, e)
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun dealException(thread: Thread, e: Throwable) : File {
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

        // 私有目录
        val file = File(context.externalCacheDir?.absoluteFile, "crash_info")
//        val file = File(context.cacheDir?.absoluteFile, "crash_info")
        if (!file.exists()){
            file.mkdirs()
        }
        val crashFile = File(file, time + FILE_NAME_SUFFIX)
        Log.e(TAG, "dealException:  ${crashFile.absolutePath}" )
        val pw = PrintWriter(BufferedWriter(FileWriter(crashFile))).apply {
            println(time)
            println("Thread : ${thread.name}")
            println(getPhoneInfo())
        }
        e.printStackTrace(pw)       // 写入 Crash 堆栈
        pw.flush()
        pw.close()
        return crashFile
    }



    @Throws(PackageManager.NameNotFoundException::class)
    private fun getPhoneInfo(): String? {
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        return StringBuilder().apply {
            //  App版本
            append("App Version : ")
            append("versionName : ${pi.versionName} _ versionCode : ${pi.versionCode}\n")

            // Android 版本号
            append("OS Version : ")
            append("Android ${Build.VERSION.RELEASE} _ SDK ${Build.VERSION.SDK_INT}\n")

            // 手机厂商
            append("Vendor : ${Build.MANUFACTURER}\n")

            // 手机型号
            append("Model : ${Build.MODEL}\n")

            //CPU架构
            append(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Build.SUPPORTED_ABIS.toString()
                }else{
                    Build.CPU_ABI
                }
            )
        }.toString()

    }
}

