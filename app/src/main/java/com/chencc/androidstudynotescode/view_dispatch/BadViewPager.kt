package com.chencc.androidstudynotescode.view_dispatch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class BadViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    /**
     * 外部拦截法
     * 在父view处理
     */
    private var mLastX = 0
    private var mLastY = 0

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(ev)
            return false
        }
        return true
//        var x = ev?.x?.toInt() ?: 0
//        var y = ev?.x?.toInt() ?: 0
//        when(ev?.action){
//            MotionEvent.ACTION_DOWN -> {
//                mLastX = ev.x.toInt()
//                mLastY = ev.y.toInt()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val deltax = x - mLastX
//                val deltay = y - mLastY
//                if (abs(deltax) > abs(deltay)){
//                    return true
//                }
//            }
//        }
//        return super.onInterceptTouchEvent(ev)
    }
}