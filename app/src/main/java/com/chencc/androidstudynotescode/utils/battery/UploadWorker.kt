package com.chencc.androidstudynotescode.utils.battery

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "UploadWorker"
class UploadWorker(val context: Context, private val  parameters: WorkerParameters) : Worker(context, parameters){
    override fun doWork(): Result {
        // 执行后台任务
        Log.i(TAG, "doWork:  执行 doWork 后台任务...")
        return Result.success()
    }
}