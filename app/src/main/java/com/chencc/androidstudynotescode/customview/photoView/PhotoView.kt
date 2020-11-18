
package com.chencc.androidstudynotescode.customview.photoView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.utils.dp2px
import com.chencc.androidstudynotescode.utils.getBitmap
import kotlin.math.max
import kotlin.math.min

/**
 * PhotoView
 *
 * 缩放偏移的滚动边界需要优化
 */
private const val TAG = "PhotoView"
class PhotoView(var mContext : Context, attrs : AttributeSet? = null)  : View(mContext,attrs) {

    private val bitmap = getBitmap(resources, R.drawable.image2, 300f.dp2px.toInt())

    /**
     * 绘制起点
     */
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 图片偏移量
     */
    private var offsetX = 0f
    private var offsetY = 0f
    /**
     * 缩放模式
     */
    private var currentScale = 1f
    private var smallScale = 1f
    private var bigScale = 1f

    /**
     * 双击以及惯性
     */
    private val gestureDetector = GestureDetector(mContext, PhotoGestureDetector())

    private var scaleGestureDetector = ScaleGestureDetector(mContext, PhotoScaleGestureListener())

    private var overScroller = OverScroller(mContext)

    /**
     * 放大模式的放大系数
     */
    private val OVER_SCALE_FACTOR = 1.5f
    /**
     * 是否是放大状态
     */
    private var isEnlarge = false



    override fun onDraw(canvas: Canvas) {
        // 当前缩放比例
        val scaleFaction = (currentScale - smallScale) / (bigScale - smallScale)
        Log.i(TAG, "onDraw:  offsetX :  ${offsetX}    offsetY :: ${offsetY} ")
        // 根据当前缩放比例设置偏移量

        canvas.translate(offsetX * scaleFaction , offsetY * scaleFaction )
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        if ((bitmap.width.toFloat() / width.toFloat()) > (bitmap.height.toFloat() / height.toFloat())){
            // 以宽为准
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * OVER_SCALE_FACTOR
        }else{
            // 以高为准
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * OVER_SCALE_FACTOR
        }
        currentScale = smallScale
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 触摸事件以缩放优先
        var result = scaleGestureDetector.onTouchEvent(event)
        // 如果不是正在缩放，则交给 gestureDetector 处理
        if (!scaleGestureDetector.isInProgress){
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }



    fun getCurrentScale() = currentScale
    fun setCurrentScale(scale : Float){
        currentScale = scale
        invalidate()
    }

    /**
     * 双击放大缩小的动画
     */
    private fun getScaleAnimation(start: Float = smallScale,  stop : Float = bigScale) : ObjectAnimator{
        return ObjectAnimator.ofFloat(this, "currentScale", start, stop)
    }

    /**
     * offset的边界处理
     */
    private fun fixOffsets(){
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    /**
     * 处理双击以及惯性
     */
    inner class PhotoGestureDetector : GestureDetector.SimpleOnGestureListener() {
        /**
         * UP 事件触发， 单机或双击的第一次事件触发
         * UP 时 如果不是双击的第二次点击， 不是长按 则触发
         */
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return super.onSingleTapUp(e)
        }

        /**
         * 按下触发
         * 只关心返回值即可
         */
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        /**
         * UP 之后惯性滑动 > 50dp
         */
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (isEnlarge){
                val scaleFaction = (currentScale - smallScale) / (bigScale - smallScale)

                val fixVelocityX = if (bitmap.width * currentScale > width) velocityX.toInt() else 0
                val fixVelocityY = if (bitmap.height * currentScale > height) velocityY.toInt() else 0

                val minX = if (bitmap.width * currentScale > width)  ((-(bitmap.width * currentScale - width) / 2 / scaleFaction).toInt())  else  0
                val maxX = if (bitmap.width * currentScale > width)  (((bitmap.width * currentScale - width) / 2 / scaleFaction).toInt())  else  0

                val minY = if (bitmap.height * currentScale > height) ( (-(bitmap.height * currentScale - height) / 2 / scaleFaction).toInt() ) else 0
                val maxY = if (bitmap.height * currentScale > height) ( ((bitmap.height * currentScale - height) / 2 / scaleFaction).toInt() ) else 0

                overScroller.fling(
                    offsetX.toInt(), offsetY.toInt(), fixVelocityX, fixVelocityY,
                    minX,
                    maxX,
                    minY,
                    maxY,
                    0 , 0
                )
                postOnAnimation(FlingRunner())
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }

        /*
         * 双击的第二次点击 down 触发
         * 双击的触发时间 40ms - 300ms
         */
        override fun onDoubleTap(e: MotionEvent): Boolean {

            isEnlarge = !isEnlarge
            if (isEnlarge){
                // 缩小状态的距离 - 放大状态距离 = 偏移量
                offsetX = (e.x - width / 2f)  - (e.x - width / 2f) * bigScale / smallScale
                offsetY = (e.y - height / 2f)  - (e.y - height / 2f) * bigScale / smallScale
                fixOffsets()
                getScaleAnimation().start()
            }else{
                getScaleAnimation(stop = currentScale).reverse()
            }
            return super.onDoubleTap(e)
        }

        /**
         * 滚动事件触发  move
         * @param e1 手指按下事件
         * @param e2 当前事件
         * @param distanceX 偏移量
         * @param distanceY 偏移量
         */
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            Log.i(TAG, "onScroll:   distanceX : $distanceX    distanceY  :  $distanceY")
            if (isEnlarge){
                val scaleFaction = (currentScale - smallScale) / (bigScale - smallScale)

                if (bitmap.width * currentScale > width){
                    val maxOffset = (bitmap.width * currentScale - width) / 2
                    offsetX -= distanceX
                    val realOffset = offsetX * scaleFaction

                    if (realOffset < -maxOffset ){
                        offsetX = -maxOffset / scaleFaction
                    }
                    if (realOffset > maxOffset){
                        offsetX = maxOffset / scaleFaction
                    }
                }
                if (bitmap.height * currentScale > height){

                    val maxOffset = (bitmap.height * currentScale - height) / 2
                    val realOffset = offsetY * scaleFaction

                    offsetY -= distanceY

                    if (realOffset < -maxOffset ){
                        offsetY = -maxOffset / scaleFaction
                    }
                    if (realOffset > maxOffset ){
                        offsetY = maxOffset / scaleFaction
                    }
                }
                fixOffsets()
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onContextClick(e: MotionEvent?): Boolean {
            return super.onContextClick(e)
        }

        /**
         * 延时 300ms 触发 TAP 事件
         * 单机按下时触发，双击时不触发，  down up 都有可能触发
         * 300ms 以内抬手，才会触发 TAP -- onSingleTapConfirmed
         * 300ms 以后抬手，不是双击，不是长按则触发
         */
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return super.onSingleTapConfirmed(e)
        }

        /**
         * 延时 100ms 触发
         * 处理点击效果
         */
        override fun onShowPress(e: MotionEvent?) {
            super.onShowPress(e)
        }

        /**
         * 双击的第二次 down up move 触发
         */
        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return super.onDoubleTapEvent(e)
        }

        /**
         * 长按，默认 300ms 触发
         */
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }
    }

    /**
     * 双指缩放处理
     */
    inner class PhotoScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener{

        var initScale = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // 当前缩放倍数 大于最小倍数 并且不是放大，就重置为放大模式
            if (!isEnlarge){
                offsetX = 0f
                offsetY = 0f
            }
            if ((currentScale >= smallScale && !isEnlarge)
                || (currentScale == smallScale && isEnlarge)){
                isEnlarge = !isEnlarge
                if (!isEnlarge){
                    offsetX = 0f
                    offsetY = 0f
                }
            }
            // 初始倍数大小 * 放大因子 = 缩放的倍数
            currentScale = min(bigScale , (initScale * detector.scaleFactor))
            currentScale = max(currentScale , smallScale)
            invalidate()
            return false
        }

    }

    inner class FlingRunner : Runnable{
        override fun run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.currX.toFloat();
                offsetY = overScroller.currY.toFloat();
                invalidate()
                // 下一帧动画的时候执行
                postOnAnimation(this);
            }
        }

    }
}

