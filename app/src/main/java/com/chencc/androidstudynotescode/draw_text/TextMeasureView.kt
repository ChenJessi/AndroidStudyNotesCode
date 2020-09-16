package com.chencc.androidstudynotescode.draw_text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


private const val TAG = "TextMeasureView"
/**
 * 文字测量演示
 */
class TextMeasureView : View {

    private var mText = "文字测量演示"
    private val mTextPaint by lazy {
        Paint().apply {
            textSize = 180f
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.RED
        }
    }
    private val mLinePaint by lazy {
        Paint().apply {
            strokeWidth = 2f
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.GRAY
        }
    }

    private val mDashPathEffect by lazy { DashPathEffect(floatArrayOf(8f, 4f), 0f) }
    private val mTextBounds: Rect by lazy { Rect() }

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 1. 测量文字
        measureText()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null){
            return
        }
        /**
         * 文字偏移量
         *  其实就是 文字 宽高的一半
         */
        val offsetx = (mTextBounds.left + mTextBounds.right) / 2
        val offsety = (mTextBounds.top + mTextBounds.bottom) / 2

        val width = measuredWidth
        val height = measuredHeight

        val halfWidth = width / 2f
        val halfHeight = height / 2f

        /**
         *  文字居中显示
         */
        val x = halfWidth - offsetx
        val y = halfHeight - offsety
        // 绘制文字
        canvas.drawText(mText, x, y, mTextPaint)

       // 绘制纵横中线
        canvas.drawLine(0f, halfHeight, width.toFloat(), halfHeight , mLinePaint)
        canvas.drawLine(halfWidth, 0f,  halfWidth, height.toFloat() , mLinePaint)
        Log.e(TAG, "onDraw:  $mTextBounds   $x   $y" )
        Log.e(TAG, "onDraw:  ${mTextBounds.right}   $x   $y" )

        /**
         * 基准线的坐标 + Bounds 相对于基准线的坐标
         */
        //top
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.top, x + mTextBounds.right, y + mTextBounds.top, mLinePaint)
        // bottom
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.bottom , x + mTextBounds.right, y + mTextBounds.bottom, mLinePaint)
        // left
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.top , x + mTextBounds.left, y + mTextBounds.bottom, mLinePaint)
        // right
        canvas.drawLine(x + mTextBounds.right, y + mTextBounds.top , x + mTextBounds.right, y + mTextBounds.bottom, mLinePaint)


    }





    /**
     * 测量文字
     */
    private fun measureText(){
        mTextPaint.getTextBounds(mText, 0, mText.length, mTextBounds)


    }



}