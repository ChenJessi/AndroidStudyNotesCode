package com.chencc.androidstudynotescode.materialdesign.nestedscroll.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

private const val TAG = "MyNestedScrollChildL"

/**
 *
 *
 */
class MyNestedScrollChildL : LinearLayout, NestedScrollingChild{


    private var helper : NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        helper.isNestedScrollingEnabled = true

    }


    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.i(TAG, "setNestedScrollingEnabled:  $enabled")
        helper.isNestedScrollingEnabled = enabled
    }

    /**
     * 是否可以嵌套滑动
     */
    override fun isNestedScrollingEnabled(): Boolean {
        Log.i(TAG, "isNestedScrollingEnabled: ")
        return helper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.i(TAG, "startNestedScroll:  $axes")
        return helper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        Log.i(TAG, "stopNestedScroll: ")
        helper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.i(TAG, "hasNestedScrollingParent: ")
        return helper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {

        Log.i(TAG, "dispatchNestedScroll: =======>>>>>    dxConsumed : $dxConsumed  dyConsumed : $dyConsumed  dxUnconsumed : $dxUnconsumed " +
                "dyUnconsumed  : $dyUnconsumed   offsetInWindow : $offsetInWindow")
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.i(TAG, "dispatchNestedFling:   velocityX : $velocityX  velocityY : $velocityY  consumed : $consumed")
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "dispatchNestedPreFling:  velocityX : $velocityX  velocityY : $velocityY")
        return helper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        Log.i(TAG, "dispatchNestedPreScroll:  dx : $dx  dy : $dy   consumed : $consumed  offsetInWindow : $offsetInWindow")
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }


    private var realHeight = 0
    private var heightSpec = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        realHeight = 0
        heightSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount){
            val view = getChildAt(i)
            heightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED)
            measureChild(view, widthMeasureSpec, heightSpec)
            Log.i(TAG, "child  onMeasure :  getMeasureHeight : ${view.measuredHeight}")
            realHeight += view.measuredHeight
        }
        Log.i(TAG, "child  onMeasure :  realHeight : $realHeight ")
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightSpec))
    }


    private var mLastTouchX = 0
    private var mLastTouchY = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val consumed = IntArray(2)
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mLastTouchX = (event.rawX + 0.5).toInt()
                var nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE
                nestedScrollAxis = nestedScrollAxis or ViewCompat.SCROLL_AXIS_HORIZONTAL
                startNestedScroll(nestedScrollAxis)
            }
            MotionEvent.ACTION_MOVE ->{
                val x = event.rawX
                val y = event.rawY
                val dx = mLastTouchX - x
                var dy = mLastTouchY - y
                mLastTouchX = x.toInt()
                mLastTouchY = y.toInt()
                /**
                 * 如果父View 消耗了事件，及可以嵌套滚动
                 * 则先滚动父View 父View
                 * 滚动完成之后，剩余的距离交给子View进行滚动
                 *  consumed  父View 消耗的 x y 的距离
                 */
                if (dispatchNestedPreScroll(dx.toInt(), dy.toInt(), consumed, null)){
                    Log.i(TAG, "onTouchEvent:  dy : $dy   consumed : $consumed")
                    dy -= consumed[1]
                    when (dy) {
                        0f -> {
                            Log.i(TAG, "onTouchEvent:  1111 dy =====>>> : $dy")
                            return  true
                        }
                        else -> {
                            Log.i(TAG, "onTouchEvent:  2222 dy =====>>> : $dy ")
                            scrollBy(0, dy.toInt())
                        }
                    }

                }

            }
            MotionEvent.ACTION_UP , MotionEvent.ACTION_CANCEL -> {
                stopNestedScroll()
            }
        }
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        Log.i("onMeasure","y:  $y  , getScrollY:    $scrollY  , height:  $height    , realHeight:  $realHeight   , --   ${(realHeight - height)}")
        var sy = y
        if (sy < 0){
            sy = 0
        }
        if (sy > realHeight){
            sy = realHeight
        }
        if (sy != scrollY){
            Log.i(TAG, "scrollTo:  $y")
            super.scrollTo(x, y)
        }
    }


}