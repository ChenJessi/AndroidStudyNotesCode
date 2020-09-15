package com.chencc.androidstudynotescode.view_dispatch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MySwipeRefreshLayout : SwipeRefreshLayout {
    private  val TAG = "MySwipeRefreshLayout"
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val x = ev?.x?.toInt() ?: 0
        val y = ev?.y?.toInt() ?: 0

        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {

            }
        }


        return super.dispatchTouchEvent(ev)
    }
}