package com.chencc.androidstudynotescode.customview.photoView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.utils.dp2px
import com.chencc.androidstudynotescode.utils.getBitmap

/**
 * 多指触摸
 */
class MultiTouchEvent(var mContext : Context, attrs: AttributeSet?)  : View(mContext, attrs)  {


    private val bitmap = getBitmap(resources, R.drawable.image2, 300f.dp2px.toInt())

    /**
     * 手指滑动的偏移值
     */
    private var offsetX = 0f
    private var offsetY = 0f

    /**
     * 最后的偏移量
     */
    private var lastOffsetX = 0f
    private var lastOffsetY = 0f
    /**
     * 手指按下的坐标
     */
    private var downX = 0f
    private var downY = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 当前手指的id
     * 可以拖动图片的手指
     */
    private var currentPointId = 0

    override fun onDraw(canvas: Canvas) {

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                // 只触发一次
                downX = event.x
                downY = event.y
                lastOffsetX = offsetX
                lastOffsetY = offsetY
                currentPointId = 0
            }
            MotionEvent.ACTION_MOVE -> {
                // 获取当前按下手指的 index
                // 通过index 拿到id
                val index = event.findPointerIndex(currentPointId)
                offsetX = lastOffsetX + event.getX(index) - downX
                offsetY = lastOffsetY + event.getY(index) - downY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 当前按下手指的 index
                val index = event.actionIndex
                currentPointId = event.getPointerId(index)
                // 将按下坐标和偏移量重置， 以最后按下的手指为准
                downX = event.getX(currentPointId)
                downY = event.getY(currentPointId)
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // 抬起的时候
                var upIndex = event.actionIndex
                val pointerId = event.getPointerId(upIndex)
                // 只判断活跃的手指即可
                if (pointerId == currentPointId){
                    if (upIndex == event.pointerCount - 1){
                        // 当前活跃的手指是最后一根手指，并且离开 将事件交给前一个手指
                        upIndex = event.pointerCount - 2
                    }else{
                        // 否则就交给它的下一个手指处理
                        upIndex++
                    }
                    currentPointId = event.getPointerId(upIndex)
                    downX = event.getX(currentPointId)
                    downY = event.getY(currentPointId)
                    lastOffsetX = offsetX
                    lastOffsetY = offsetY
                }
            }

        }

        return true
    }

}