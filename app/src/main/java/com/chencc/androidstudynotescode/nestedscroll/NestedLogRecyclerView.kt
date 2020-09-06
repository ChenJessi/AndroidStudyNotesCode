package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class NestedLogRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.e("RecyclerViewNestedLog", "setNestedScrollingEnabled");
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        Log.e("RecyclerViewNestedLog", "isNestedScrollingEnabled");
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.e("RecyclerViewNestedLog", "startNestedScroll");
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.e("RecyclerViewNestedLog", "startNestedScroll");
        return super.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        Log.e("RecyclerViewNestedLog", "stopNestedScroll");
        super.stopNestedScroll()
    }

    override fun stopNestedScroll(type: Int) {
        Log.e("RecyclerViewNestedLog", "stopNestedScroll");
        super.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.e("RecyclerViewNestedLog", "hasNestedScrollingParent");
        return super.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedScroll");
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedScroll");
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreScroll");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreScroll");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedFling");
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreFling");
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }
}