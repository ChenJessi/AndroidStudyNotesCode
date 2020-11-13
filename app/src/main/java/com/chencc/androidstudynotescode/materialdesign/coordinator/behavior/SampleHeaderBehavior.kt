package com.chencc.androidstudynotescode.materialdesign.coordinator.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

/**
 * 跟随滚动
 */

private const val TAG = "SampleHeaderBehavior"
class SampleHeaderBehavior(var mContext : Context, var attrs : AttributeSet) : CoordinatorLayout.Behavior<TextView>(mContext, attrs) {

    private var mLayoutTop = 0
    private var mOffsetTopAndBottom = 0

    override fun onLayoutChild(parent: CoordinatorLayout, child: TextView, layoutDirection: Int): Boolean {
        parent.onLayoutChild(child, layoutDirection)
        mLayoutTop = child.top
        Log.i(TAG, "onLayoutChild:  mLayoutTop  : $mLayoutTop")
        return true
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: TextView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    /**
     * 当滚动开始执行的时候回调这个方法。
     *
     * @param dy 【注意】  dy > 0 为向上滚动
     */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: TextView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.i(TAG, "onNestedPreScroll:   dy : $dy")
        var consumedy = 0 // 记录我们消费的距离
        // 本次滚动到的位置  上次滚动到的位置 - 本次滚动距离
        var offset = mOffsetTopAndBottom - dy
        var minOffset =  -getChildScrollRang(child)
        var maxOffset = 0
        offset = max(offset , minOffset)
        offset = min(offset, maxOffset)
        // child.top - mLayoutTop  当前顶部位置 - 初始顶部位置 = 当前滚动的距离
        // offset - (child.top - mLayoutTop)   当前要滚动到的位置 - 当前滚动的距离 = 还需要滚动的距离
        ViewCompat.offsetTopAndBottom(child, offset - (child.top - mLayoutTop))
        // 上次位置- 本次滚动的位置 = 本次滚动的距离
        consumedy = mOffsetTopAndBottom - offset
        // 本次滚动到的位置
        mOffsetTopAndBottom = offset
        consumed[1] = consumedy
    }


    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: TextView, target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }


    /**
     * 边界判断
     * 获取最大可滑动距离
     */
    private fun getChildScrollRang(child : View) : Int{
        return  child.height
    }
}