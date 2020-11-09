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

    /**
     * 启用或者禁用嵌套滚动
     * 设置为 true 时，表示当前界面View的层次结构是支持嵌套滚动的， （即 NestedScrollingParent 嵌套 NestedScrollingChild）才会触发嵌套滚动
     *  一般直接代理给 NestedScrollingChildHelper 的同名方法即可
     */
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.i(TAG, "setNestedScrollingEnabled:  $enabled")
        helper.isNestedScrollingEnabled = enabled
    }

    /**
     * 是否可以嵌套滑动
     * 一般直接代理给 NestedScrollingChildHelper 同名方法即可
     */
    override fun isNestedScrollingEnabled(): Boolean {
        Log.i(TAG, "isNestedScrollingEnabled: ")
        return helper.isNestedScrollingEnabled
    }

    /**
     * 表示 View 开始滑动，一般是在  ACTION_DOWN 中调用
     * @return 如果返回为true 表示父布局支持嵌套滚动
     *  一般是直接代理给 NestedScrollingChildHelper 同名方法即可
     *  这时，正常情况下会触发 Parent 的 onStartNestedScroll() 方法
     */
    override fun startNestedScroll(axes: Int): Boolean {
        Log.i(TAG, "startNestedScroll:  $axes")
        return helper.startNestedScroll(axes)
    }

    /**
     * 一般在时间结束 action_up 或者 action_cancel 中调用，告诉父布局结束滚动
     * 一般直接代理给 NestedScrollingChildHelper 同名方法即可
     */
    override fun stopNestedScroll() {
        Log.i(TAG, "stopNestedScroll: ")
        helper.stopNestedScroll()
    }

    /**
     * 判断当前 View 是否有滑动嵌套的 Parent
     * 一般直接代理给 NestedScrollingChildHelper 同名方法即可
     */
    override fun hasNestedScrollingParent(): Boolean {
        Log.i(TAG, "hasNestedScrollingParent: ")
        return helper.hasNestedScrollingParent()
    }

    /**
     * 在当前View 消费滚动距离之后，把剩下的滚动距离传给父布局。如果当时没有发生嵌套滚动，或者不支持嵌套滚动，调用该方法也没啥用
     * 一般也是直接代理给 NestedScrollingChildHelper 同名方法
     * @param dxConsumed 被当前View 消费了的水平滑动距离
     * @param dyConsumed 被当前View 消费了的垂直滑动距离
     * @param dxUnconsumed 未被消费的水平滑动距离
     * @param dyUnconsumed 未被消费的垂直滑动距离
     * @param offsetInWindow 输出可选参数，如果不是 null，在方法完成时返回。
     *                      会将该视图从该操作完成之前到操作完成之后的本地视图坐标中的偏移量封装进该参数中，
     *                       offsetInWindow[0] 水平方向  offsetInWindow[1] 垂直方向
     * @return true 滚动事件分发成功  false 分发失败
     */
    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {

        Log.i(TAG, "dispatchNestedScroll: =======>>>>>    dxConsumed : $dxConsumed  dyConsumed : $dyConsumed  dxUnconsumed : $dxUnconsumed " +
                "dyUnconsumed  : $dyUnconsumed   offsetInWindow : $offsetInWindow")
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    /**
     * 将惯性滑动的速度分发给 Parent
     * 一般直接代理给 NestedScrollingChildHelper 同名方法即可
     * @param velocityX 水平滑动速度
     * @param velocityY 垂直滑动速度
     * @param consumed true 表示当前View消费了滑动事件，否则传入 false
     * @return 表示 Parent 处理了滑动事件
     */
    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.i(TAG, "dispatchNestedFling:   velocityX : $velocityX  velocityY : $velocityY  consumed : $consumed")
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    /**
     * 在当前 View 自己处理惯性滑动前，先将滑动事件分发给 Parent
     * 一般来说，如果想要自己处理惯性的滑动事件，就不应该调用该方法给 Parent 处理，
     * 如果给了 Parent 并且返回为 true 那表示 Parent 已经处理了，自己就不应该再处理。
     * 返回为 false 代表 Parent 没有处理，但是不代表后面 Parent 就不处理了
     * @return true 表示 Parent 处理了滑动事件
     */
    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "dispatchNestedPreFling:  velocityX : $velocityX  velocityY : $velocityY")
        return helper.dispatchNestedPreFling(velocityX, velocityY)
    }

    /**
     * 在View 消费滚动距离之前把滑动距离传给父布局。相当于把优先处理权交给 Parent
     * 一般直接代理 NestedScrollingChildHelper 同名方法即可
     * @param dx 当前水平方向滑动距离
     * @param dy 当前垂直方向滑动距离
     * @param consumed 输出参数会将 Parent 消费掉的距离封装进该参数
     *                  consumed[0] 水平方向 consumed[1] 垂直方向
     */
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
                mLastTouchY = (event.rawY + 0.5).toInt()
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
                 * 如果父View 消耗了事件，即可以嵌套滚动
                 * 则先滚动父View 父View
                 * 滚动完成之后，剩余的距离交给子View进行滚动
                 *  consumed  父View 消耗的 x y 的距离
                 */
                if (dispatchNestedPreScroll(dx.toInt(), dy.toInt(), consumed, null)){
                    Log.i(TAG, "onTouchEvent:  dy : $dy   consumed : $consumed")
                    dy -= consumed[1]
                    if (dy == 0f) {
                        Log.i(TAG, "onTouchEvent:  1111 dy =====>>> : $dy")
                        return  true
                    }
                }else{
                    Log.i(TAG, "onTouchEvent:  2222 dy =====>>> : $dy ")
                    scrollBy(0, dy.toInt())
                }
            }
            MotionEvent.ACTION_UP , MotionEvent.ACTION_CANCEL -> {
                stopNestedScroll()
            }
        }
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        var sy = y
        Log.i("scrollTo","y:  $y  , getScrollY:    $scrollY  , height:  $height    , realHeight:  $realHeight   , --   ${(realHeight - height)}")
        if (sy < 0){
            sy = 0
        }
        if (sy > realHeight){
            sy = realHeight
        }
        if (sy != scrollY){
            Log.i(TAG, "scrollTo:  $sy")
            super.scrollTo(x, sy)
        }
    }


}