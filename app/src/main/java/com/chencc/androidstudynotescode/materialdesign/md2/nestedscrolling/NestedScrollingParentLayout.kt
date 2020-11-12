package com.chencc.androidstudynotescode.materialdesign.md2.nestedscrolling

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.math.roundToLong


/**
 *
 *
 */
private const val TAG = "NestedScrollingParentL"
class NestedScrollingParentLayout : LinearLayout, NestedScrollingParent{

    private var mTopViewHeight = 0
    private val helper = NestedScrollingParentHelper(this)
    private val mValueAnimator = ValueAnimator()
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        orientation = VERTICAL
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onStopNestedScroll(child: View) {
        helper.onStopNestedScroll(child)
    }


    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        helper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.i(TAG, "onNestedPreScroll: target : $target   ${target.scrollY}")

//        val recylclerView = (target as ViewGroup).getChildAt(0)
//        val isShowTop = dy < 0 && scrollY > 0 &&  !target.canScrollVertically(-1) && !recyclerView.canScrollVertically(-1)
        val isHideTop = dy > 0 && scrollY < mTopViewHeight
        val isShowTop = dy < 0 && scrollY > 0 &&  !target.canScrollVertically(-1) && target.scrollY == 0
        if (isShowTop || isHideTop){
            var scrolldy = mTopViewHeight - scrollY
            var tmp = dy
            if (scrolldy < dy){
                tmp = scrolldy
            }
            scrollBy(0, tmp)
            consumed[1] = tmp
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }



    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        val distance = abs(scrollY)
        var duration = 0L
        if (velocityY > 0){   // 向上滑
            duration = 3 * (1000 * (distance / velocityY)).roundToLong()
            startAnimation(duration, scrollY, mTopViewHeight)
        }else if (velocityY < 0){      // 向下滑
            val distanceRatio = distance.toFloat() / height
            duration = ((distanceRatio + 1) * 150).toLong()
            startAnimation(duration, scrollY, 0);
        }

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = getChildAt(0).measuredHeight
    }

    override fun scrollTo(x: Int, y: Int) {
        var sy = y
        if (sy < 0){
            sy = 0
        }
        if (sy > mTopViewHeight){
            sy = mTopViewHeight
        }
        super.scrollTo(x, sy)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutParams = getChildAt(2).layoutParams
        layoutParams.height = measuredHeight - getChildAt(1).measuredHeight
        getChildAt(2).layoutParams = layoutParams
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun startAnimation(duration : Long, startY : Int , endY : Int){
        mValueAnimator.apply {
            cancel()
            removeAllListeners()
            addUpdateListener {
                val animatedValue = it.animatedValue as Int
                scrollTo(0, animatedValue)
            }
            interpolator = DecelerateInterpolator()
            setIntValues(startY, endY)
            setDuration(duration)
            start()
        }
    }
}