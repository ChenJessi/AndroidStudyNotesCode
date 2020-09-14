package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.net.nsd.NsdManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.utils.FlingHelper
import com.qmuiteam.qmui.util.QMUIDeviceHelper
import com.qmuiteam.qmui.util.QMUIDisplayHelper

/**
 * tab 吸顶的 nestedScrollLayout
 */
class NestedScrollLayout : NestedScrollView {
    private val TAG = "NestedScrollLayout"

    private val flingHelper by lazy { FlingHelper(context) }

    private var contentView : ViewGroup? = null
    private var topView : View? = null
    // 用于判断 recyclerView 是否在 fling
    private var isStartFling = false

    /**
     * y轴滑动的距离
     */
    private var totalDy = 0

    /**
     * 当前滑动的y轴加速度
     */
    private var velocityY = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    private fun init() {

        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isStartFling) {
                totalDy = 0
                isStartFling = false
            }
            if (scrollY == 0){
                Log.i(TAG, "TOP SCROLL")
            }
            Log.i(TAG, "SCROLL =====>>>  ${scrollY} ");
            //  getChildAt(0)  子View 的高度
            //  v  scrollView 的高度
            // getChildAt(0).measuredHeight - v.measuredHeight == 可以向上滑动的最大距离
            if (scrollY == (getChildAt(0).measuredHeight - v.measuredHeight)){
                Log.i(TAG, "BOTTOM SCROLL");
                dispatchChildFling()
            }
            // 在 recyclerView fling情况下，记录recyclerView在y轴的位移
            totalDy += scrollY - oldScrollY
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contentView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 调整contentView高度为父容器高度，使之填充布局，以免滚动后出现空白
        val layoutParams = contentView?.layoutParams
        layoutParams?.height = measuredHeight
        contentView?.layoutParams = layoutParams
    }

    /**
     * 滚动之前
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        // 向上滚动，若当前topView 可见，需要将topView 滑动至不可见
        val hideTop = dy > 0 && scrollY < topView?.measuredHeight ?: 0
        if (hideTop){
          scrollBy(0, dy)
          consumed[1] = dy
        }
    }
    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0){
            this.velocityY = 0
        }else {
            isStartFling = true
            this.velocityY = velocityY
        }
    }







    private fun dispatchChildFling(){
        if (velocityY != 0){
            // 计算滑动的距离
            var splineFlingDistance = flingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy){
                // 如果根据加速度计算出的滑动距离大于最大滑动距离的话
                // 就将当前剩余滑动距离计算出的速度交给子View 去继续滑动
                childFling(flingHelper.getVelocityByDistance(splineFlingDistance - totalDy))
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    /**
     * 给定y轴速度，让recyclerView继续惯性滑动
     */
    private fun childFling(velY : Int){
        contentView?.let {
            val childRecyclerView = getChildRecyclerView(it)
            childRecyclerView?.fling(0, velY)
        }
    }


    /**
     * 遍历子view 查找recyclerView
     */
    private fun getChildRecyclerView(viewGroup : ViewGroup) : RecyclerView?{
        for (index in 0 until viewGroup.childCount ){
            val view = viewGroup.getChildAt(index)
            if (view is RecyclerView){
                return view
            }else if (view is ViewGroup){
                val childRecyclerView = getChildRecyclerView(view)
                if (childRecyclerView is RecyclerView){
                    return childRecyclerView
                }
            }
        }
        return null
    }
}