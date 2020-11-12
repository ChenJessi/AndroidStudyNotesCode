package com.chencc.androidstudynotescode.materialdesign.nestedscroll.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import com.qmuiteam.qmui.util.QMUIDisplayHelper

/**
 * @author Created by CHEN on 2020/11/8
 * @email 188669@163.com
 */

private const val TAG = "MyNestedScrollParentL"

class MyNestedScrollParentL : LinearLayout, NestedScrollingParent{

    private var helper = NestedScrollingParentHelper(this)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    init {

    }
    var realHeight = 0
    var heightSpec = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        realHeight = 0
        heightSpec = heightMeasureSpec
        for (i in 0 until childCount){
            val view = getChildAt(i)
            heightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) , MeasureSpec.UNSPECIFIED)
            measureChild(view, widthMeasureSpec, heightSpec)
            realHeight += view.measuredHeight
        }
        Log.i(TAG, "onMeasure:  realHeight  :  $realHeight")
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightSpec))
    }


    /**
     * 当 NestedScrollingChild 调用 startNestedScroll() 方法时，会调用该方法。主要是通过返回值告诉系统是否需要对后续的滚动进行处理
     * @param child 该 ViewParent 包含的 NestedScrollingChild 的直接子 View，  如果只有一层嵌套， 和 target是同一个 View
     * @param target 本次嵌套滚动的 NestedScrollingChild
     * @param nestedScrollAxes 滚动方向 嵌套滑动的坐标系，  用来判断是x轴滑动还是y轴滑动，
     * @return true 表示需要进行处理 后续的滚动会触发相应的回调
     *          false 不需要处理，后续也就不会有回调了
     *
     *
     *   child 和 target 的区别：
     *   如果是嵌套两层如： Parent 里面包含一个 LinearLayout, LinearLayout 里面才是 NestedScrollingChild 类型的View , 这个时候， child 指向 LinearLayout;
     *   如果 Parent 直接包含了 NestedScrollingChild 这个时候 child 和 target 都指向 NestedScrollingChild
     */
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.i(TAG, "onStartNestedScroll:   child : $child   target : $target   nestedScrollAxes  : $nestedScrollAxes")
        return true
    }

    /**
     * 如果 onStartNestedScroll() 方法返回的是 true 的话，那么紧接着就会调用该方法，
     * 它是让嵌套滚动在开始滚动之前，让布局容器或者它的父类执行一些配置的初始化
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        Log.i(TAG, "onNestedScrollAccepted:   child :  $child   target : $target  axes : $axes")
        helper.onNestedScrollAccepted(child, target, axes)
    }

    /**
     * 停止滚动，当子View 调用 stopNestedScroll() 时会调用该方法
     */
    override fun onStopNestedScroll(child: View) {
        Log.i(TAG, "onStopNestedScroll:    child  : $child")
         helper.onStopNestedScroll(child)
    }

    /**
     * 当 子View 调用 dispatchNestedScroll() 方法时，会调用该方法。也就是开始分发处理嵌套滑动了
     * @param dxConsumed  已经被 target 消耗掉的水平方向的滑动距离
     * @param dyConsumed  已经被 target 消耗掉的垂直方向的滑动距离
     * @param dxUnconsumed  未被 target 消耗掉的垂直方向的滑动距离
     * @param dyUnconsumed  未被 target 消耗掉的垂直方向的滑动距离
     */
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.i(TAG, "onNestedScroll:  target : $target   dxConsumed  : $dxConsumed  dyConsumed  : $dyConsumed   dxUnconsumed :$dxUnconsumed  dyUnconsumed : $dyUnconsumed")
    }

    /**
     * 在滑动之前会被调用，他的作用就是子类在滑动的时候，分发一下，是否有父类需要消费滑动，
     * 这个时候父类就可以根据自己的需求进行部分消费和全部消费
     *
     *
     * 当子View 调用 dispatchNestedPreScroll() 方法时会被调用， 也就是在 NestedScrollingChild 处理滑动之前
     * 会先将机会给 Parent 处理，如果 Parent 想先消费部分滚动距离，将消费的距离放入 consumed
     * @param dx 水平滑动距离
     * @param dy 垂直滑动距离
     * @param consumed 表示 Parent 要消费的滚动距离，consumed[0]和consumed[1]分别表示父布局在x和y方向上消费的距离.
     *
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        val show = showImg(target ,dy)
        val hide = hideImg(dy)
        Log.i(TAG, "onNestedPreScroll:   show : $show  hide : $hide  dy : $dy")
        Log.i(TAG, "onNestedPreScroll:   target : ${target.scrollY}")
        if (show || hide){
            var scrolldy = getChildAt(0).height - scrollY
            var tmp = dy
            if (scrolldy < dy){
                tmp = scrolldy
            }
            consumed[1] = tmp
            scrollBy(0, tmp)
            Log.i(TAG, "Parent 滑动 :  $dy")
        }
        Log.i(TAG, "onNestedPreScroll:  scrollY  $scrollY  target : $target   dy : $dy  consumed[1] : ${consumed[1]}")
    }

    /**
     * 你可以捕获对内部的 NestedScrollingChild 的fling 事件
     * @param velocityX 水平方向滑动速度
     * @param velocityY 垂直方向滑动速度
     * @param consumed 是否被 child 消费了
     * @return true 表示消费了胡奥的功能事件
     */
    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.i(TAG, "onNestedFling:  target : $target  velocityY : $velocityY  consumed : $consumed")
        return true
    }

    /**
     * 在惯性滑动处理距离之前，会调用该方法
     *  同 onNestedPreScroll() 一样，也是给 Parent 优先处理的权利
     *  @param target 本次嵌套滚动的 NestedScrollingChild
     * @param velocityX 水平方向滑动速度
     * @param velocityY 垂直方向滑动速度
     * @return true 表示 Parent 处理本次滑动事件， child 就不要处理了
     */
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "onNestedPreFling:  target : $target   velocityY : $velocityY")
        return true
    }

    /**
     * 返回当前滑动的方向
     * 一般直接通过 NestedScrollingParentHelper.getNestedScrollAxes()返回即可
     */
    override fun getNestedScrollAxes(): Int {
        return super.getNestedScrollAxes()
    }


    override fun scrollTo(x: Int, y: Int) {
        val view = getChildAt(0)
        var sy = y
        if (sy < 0){
            sy = 0
        }
        if (sy > view.height){
            sy = view.height
        }
        if (sy != scrollY){
            super.scrollTo(x, sy)
        }
    }



    private fun showImg(target : View ,dy : Int) : Boolean{
        val view = getChildAt(0)
        Log.i(TAG, "showImg:  dy : $dy  scrollY : $scrollY   view.canScrollVertically  ${view.canScrollVertically(-1)}")
        return when {
            dy < 0 && scrollY > 0 &&  !view.canScrollVertically(-1) && target.scrollY == 0 -> {
                //  向下滚动并且 视图顶部在屏幕内 并且 子View 不可以向上滚动   target 已经滑到底部
                true
            }
            else -> false
        }
    }


    private fun hideImg(dy: Int) : Boolean{
        val view = getChildAt(0)
        Log.i(TAG, "hideImg:  dy : $dy  scrollY : $scrollY   view.height  ${view.height}")
        return when{
            dy > 0 && scrollY < view.height -> {
                // 向上滚动并且 视图顶部的位置大于 view 高度
                true
            }
            else -> false
        }
    }
}