package com.chencc.androidstudynotescode.materialdesign.md2.study

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper

class NestedScrollingChild3View : View, NestedScrollingChild3 {

    private val helper = NestedScrollingChildHelper(this)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    /**
     * 开启一个嵌套滑动，
     * @param axes 支持嵌套的滑动方向  分为水平方向，竖直方向，或不指定
     * @param type 滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     * @return  如果返回true, 表示当前子view已经找了一起嵌套滑动的view并且启用嵌套滑动
     */
    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return helper.startNestedScroll(axes)
    }

    /**
     * 子view停止嵌套滑动
     */
    override fun stopNestedScroll(type: Int) {

    }

    /**
     * 在子View 滑动之前 将事件分发给 父View 由父View 判断消耗多少
     *
     *  @param dx 水平方向嵌套滑动的子View 想要变化的距离， dx < 0 向右滑动  dx > 0 向左滑动
     *  @param dy 垂直方向嵌套滑动的子View 想要变化的距离， dy < 0 向下滑动  dy > 0 向左上滑动
     *  @param consumed 【输出参数】  子View 传给父View 的数组，用于存储父View 水平与垂直方向上消耗的距离    consumed[0] 水平方向消耗的距离  consumed[1] 垂直方向消耗的距离
     *  @param offsetInWindow 子View 在当前 window 的偏移量
     *  @return true 如果返回true 表示父 View 已经消耗完了
     */
    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    /**
     * 当父 View 消耗事件之后，子View 处理之后，又继续将事件分发给 父View 由父View 判断是否消耗剩余的距离
     *
     * @param dxConsumed     水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed     垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed   水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed   垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param offsetInWindow 子view在当前window的偏移量
     * @return 如果返回true, 表示父view又继续消耗了
     *
     */
    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int, consumed: IntArray) {

    }

    /**
     * 当子View 产生 fling 滑动时，判断父View 是否拦截 fling  如果父View处理了fling，那子view就没有办法处理fling了。
     *
     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
     *
     * @return 如果返回true, 表示父view拦截了fling
     */
    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return helper.dispatchNestedPreFling(velocityX, velocityY)
    }

    /**
     * 当父View 不拦截子View 的fling 那么子View 会调用高该方法将 fling 传递给父View 进行处理
     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
     * @return 如果返回true, 表示父view拦截了fling
     */
    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    /**
     * 判断当前子view是否拥有嵌套滑动的父view
     */
    override fun hasNestedScrollingParent(type: Int): Boolean {
        return helper.hasNestedScrollingParent(type)
    }

    /**
     * 设置当前子view是否支持嵌套滑动，如果不支持，那么父view是不能够响应嵌套滑动的
     */
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        helper.isNestedScrollingEnabled = enabled
    }

    /**
     *当前子view是否支持嵌套滑动
     */
    override fun isNestedScrollingEnabled(): Boolean {
        return helper.isNestedScrollingEnabled
    }
}