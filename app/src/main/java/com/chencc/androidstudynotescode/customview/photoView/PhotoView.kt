    package com.chencc.androidstudynotescode.customview.photoView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.utils.dp2px
import com.chencc.androidstudynotescode.utils.getBitmap

/**
 * PhotoView
 */
class PhotoView(var mContext : Context, attrs : AttributeSet? = null)  : View(mContext,attrs) {

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var smallScale = 1f
    private var bigScale = 1f
    private var currentScale = 1f
    private val gestureDetector = GestureDetector(mContext, PhotoGestureDetector())
    private var bitmap : Bitmap = getBitmap(resources, R.drawable.timg, 300f.dp2px.toInt())

    override fun onDraw(canvas: Canvas) {
        canvas.scale(currentScale, currentScale, width / 2f , height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f
        if ((width.toFloat() / height.toFloat()) > (bitmap.width.toFloat() / bitmap.height.toFloat())){
            // 以宽为准
            smallScale = height.toFloat() / bitmap.height.toFloat()
            bigScale = width.toFloat() / bitmap.width.toFloat()
        }else {
            // 以高为准
            smallScale = width.toFloat() / bitmap.width.toFloat()
            bigScale = height.toFloat() / bitmap.height.toFloat()
        }
        currentScale = smallScale
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return gestureDetector.onGenericMotionEvent(event)
    }


    inner class PhotoGestureDetector : GestureDetector.SimpleOnGestureListener(){

        /**
         * UP 触发  单击或者双击的第一次会触发
         * UP 时，如果不是双击的第二次点击，不是长按 则触发
         */
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return super.onSingleTapUp(e)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        /**
         * 双击的第二次点击时触发  双击的触发时间 -- 40ms 到 300ms
         */
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onContextClick(e: MotionEvent?): Boolean {
            return super.onContextClick(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return super.onSingleTapConfirmed(e)
        }

        override fun onShowPress(e: MotionEvent?) {
            super.onShowPress(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return super.onDoubleTapEvent(e)
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }
    }
}


