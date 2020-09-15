package com.chencc.androidstudynotescode.view_dispatch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * 滑动冲突处理
 */
class DispatchRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

//    /**
//     * 内部拦截法
//     * 子view 处理事件冲突
//     */
//    private var mLastX = 0
//    private var mLastY = 0
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        var x = ev?.x?.toInt() ?: 0
//        var y = ev?.y?.toInt() ?: 0
//        when(ev?.action){
//            MotionEvent.ACTION_DOWN -> {
//                parent.requestDisallowInterceptTouchEvent(true)
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val deltaX = x - mLastX
//                val deltaY = y - mLastY
//                if (abs(deltaX) > abs(deltaY)){
//                    parent.requestDisallowInterceptTouchEvent(false)
//                }
//            }
//        }
//        mLastX = x ?: 0
//        mLastY = y ?: 0
//        return super.dispatchTouchEvent(ev)
//    }
}