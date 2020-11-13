package com.chencc.androidstudynotescode.materialdesign.coordinator.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat
import kotlin.math.abs

/**
 * 可以拖动的View
 * 演示 behavior 专用
 */
private const val TAG = "DependedView"
class DependedView : View{

    private var mDragSlop : Int = 0
    private var mLastY = 0f
    private var mLastX = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
//        mDragSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y

            }
            MotionEvent.ACTION_MOVE -> {
                val dx =  event.x - mLastX
                val dy =  event.y - mLastY
                mLastX = event.x
                mLastY = event.y
                Log.i(TAG, "onTouchEvent:  ACTION_MOVE     $dx  $dy  $x  $y  $mLastY    $mLastX")
                if (abs(dx) > mDragSlop || abs(dy) > mDragSlop){
                    ViewCompat.offsetLeftAndRight(this, dx.toInt())
                    ViewCompat.offsetTopAndBottom(this, dy.toInt())
                }
            }
            MotionEvent.ACTION_UP -> {}
        }

        return true
    }
}