package com.chencc.androidstudynotescode.materialdesign.nestedscroll.study

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper

/**
 * NestedScrollingParent3
 */
class NestedScrollingParent3View : LinearLayout, NestedScrollingParent3 {

    private val helper = NestedScrollingParentHelper(this)


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    /**
     * 当父view接受嵌套滑动，当onStartNestedScroll方法返回true该方法会调用
     *
     * @param type 滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        helper.onNestedScrollAccepted(child, target, axes, type)
    }

    /**
     * 有嵌套滑动来了，判断父View 是否接收嵌套滑动
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @param type             滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        // 逻辑处理
        return true
    }

    /**
     * 嵌套滑动的子 View 滑动之前，判断父 View 是否优先于子View 处理 ( 也就是父View 可以先消耗，然后给子View 消耗)
     * @param target 嵌套滑动的 子View
     * @param dx 水平方向嵌套滑动的子View 想要滑动的距离
     * @param dy 垂直方向嵌套滑动的子View 想要滑动的距离
     * @param 【输出参数】 这个参数要在我们实现这个函数的时候就指定，回头告诉子View当前父View 消耗的距离
     *          consumed[0] 水平消耗的距离  consumed[1] 垂直消耗的距离  好让子View 做出相应的调整
     *
     * @param type 滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果  ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        // 自己逻辑的处理
    }

    /**
     * 嵌套滑动的子View 滑动之后，判断父View 是否继续处理，(也就是父View 消耗一定距离之后，子View 再消耗，最后再判断父View 消耗不)
     * @param target 嵌套滑动的子View
     * @param dxConsumed 嵌套滑动的子View 在水平方向的滑动距离(消耗的距离)
     * @param dyConsumed 嵌套滑动的子View 在垂直方向的滑动距离(消耗的距离)
     * @param dxUnconsumed 嵌套滑动的子View 在水平方向的未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 嵌套滑动的子View 在垂直方向的未滑动的距离(未消耗的距离)
     * @param type 滑动类型  ViewCompat.TYPE_NON_TOUCH fling 效果    ViewCompat.TYPE_TOUCH 手势滑动
     * @param consumed 【输出参数】
     */
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {

    }

    /**
     * 嵌套滑动的子View 滑动之后，判断父View 是否继续处理，(也就是父View 消耗一定距离之后，子View 再消耗，最后再判断父View 消耗不)
     * @param target 嵌套滑动的子View
     * @param dxConsumed 嵌套滑动的子View 在水平方向的滑动距离(消耗的距离)
     * @param dyConsumed 嵌套滑动的子View 在垂直方向的滑动距离(消耗的距离)
     * @param dxUnconsumed 嵌套滑动的子View 在水平方向的未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 嵌套滑动的子View 在垂直方向的未滑动的距离(未消耗的距离)
     * @param type 滑动类型  ViewCompat.TYPE_NON_TOUCH fling 效果    ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {

    }

    /**
     *  嵌套滑动结束
     *  @param type 滑动类型 ViewCompat.TYPE_NON_TOUCH fling 效果  ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onStopNestedScroll(target: View, type: Int) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    override fun getNestedScrollAxes(): Int {
        return helper.nestedScrollAxes
    }
}