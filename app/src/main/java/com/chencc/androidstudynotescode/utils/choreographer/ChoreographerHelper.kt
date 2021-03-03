package com.chencc.androidstudynotescode.utils.choreographer

import android.os.Build
import android.util.Log
import android.view.Choreographer

private const val TAG = "ChoreographerHelper"

fun start() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            var lastFrameTimeNanos = 0L
            override fun doFrame(frameTimeNanos: Long) {
                // 上次回调时间
                if (lastFrameTimeNanos == 0L) {
                    lastFrameTimeNanos = frameTimeNanos
                    Choreographer.getInstance().postFrameCallback(this)
                    return
                }

                val diff = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000
                if (diff > 16.6f) {
                    // 掉帧数
                    val droppedCount = (diff / 16.6).toInt()
                    Log.e(TAG, "doFrame:  droppedCount : $droppedCount")
                }
                lastFrameTimeNanos = frameTimeNanos
                Choreographer.getInstance().postFrameCallback(this);
            }
        })
    }
}
