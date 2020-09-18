package com.chencc.androidstudynotescode.draw_text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


private const val TAG = "TextMeasureView"
/**
 * ���ֲ�����ʾ
 */
class TextMeasureView : View {

    private var mText = "���ֲ�����ʾ"
    private val mTextPaint by lazy {
        Paint().apply {
            textSize = 120f
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
    private var mMeasureWidth = 0f
    private val fontMetrics by lazy { Paint.FontMetrics() }

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
        // 1. ��������
        measureText()
        //2. ��������

        //2. ��������
        val width: Int = measureWidth(widthMeasureSpec)
        val height: Int = measureHeight(heightMeasureSpec)
        //3. ���ֲ����ߴ�
        setMeasuredDimension(width, height)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null){
            return
        }
        /**
         * ����ƫ����
         *  ��ʵ���� ���� ��ߵ�һ��
         */
        val offsetx = (mTextBounds.left + mTextBounds.right) / 2
        val offsety = (mTextBounds.top + mTextBounds.bottom) / 2

        val width = measuredWidth
        val height = measuredHeight

        val halfWidth = width / 2f
        val halfHeight = height / 2f

        /**
         *  ���־�����ʾ
         */
        val x = halfWidth - offsetx
        val y = halfHeight - offsety
        // ��������

        canvas.drawText(mText, x, y, mTextPaint)

       // �����ݺ�����
        canvas.drawLine(0f, halfHeight, width.toFloat(), halfHeight , mLinePaint)
        canvas.drawLine(halfWidth, 0f,  halfWidth, height.toFloat() , mLinePaint)
        Log.e(TAG, "onDraw:  $mTextBounds   $x   $y  $mMeasureWidth ")

//        Rect(10, -148 - 1053, 22)   9.0   912.0  1062.0
        mLinePaint.color = Color.BLACK

        // ����  mMeasureWidth
        canvas.drawLine(x + mMeasureWidth + mTextBounds.left, 0f, x + mMeasureWidth + mTextBounds.left, height.toFloat(), mLinePaint);

        /**
         * ��׼�ߵ����� + Bounds ����ڻ�׼�ߵ�����
         */
        //top
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.top, x + mTextBounds.right, y + mTextBounds.top, mLinePaint)
        // bottom
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.bottom , x + mTextBounds.right, y + mTextBounds.bottom, mLinePaint)
        // left
        canvas.drawLine(x + mTextBounds.left, y + mTextBounds.top , x + mTextBounds.left, y + mTextBounds.bottom, mLinePaint)
        // right
        canvas.drawLine(x + mTextBounds.right, y + mTextBounds.top , x + mTextBounds.right, y + mTextBounds.bottom, mLinePaint)

        mLinePaint.color = Color.RED
        canvas.drawLine(0f, y, width.toFloat(), y , mLinePaint)



        mLinePaint.color = Color.GREEN
        val descent = y + fontMetrics.descent
        canvas.drawLine(0f,descent, width.toFloat(), descent , mLinePaint)


        mLinePaint.color = Color.BLUE
        val ascent = y + fontMetrics.ascent
        canvas.drawLine(0f,ascent, width.toFloat(), ascent , mLinePaint)


        mLinePaint.color = Color.YELLOW
        val top = y + fontMetrics.top
        canvas.drawLine(0f,top, width.toFloat(), top , mLinePaint)

        mLinePaint.color = Color.MAGENTA
        val bottom = y + fontMetrics.bottom
        canvas.drawLine(0f,bottom, width.toFloat(), bottom , mLinePaint)

        mLinePaint.color = Color.CYAN
        val leading = y + fontMetrics.leading
        canvas.drawLine(0f,leading, width.toFloat(), leading , mLinePaint)

    }





    /**
     * ��������
     */
    private fun measureText(){
        // �����ı���Χ
        mTextPaint.getTextBounds(mText, 0, mText.length, mTextBounds)
        // �����ı����
        mMeasureWidth = mTextPaint.measureText(mText)

        mTextPaint.getFontMetrics(fontMetrics)
    }

    private fun measureWidth(widthMeasureSpec : Int) : Int{
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var result = 0
        when(mode){
            MeasureSpec.EXACTLY ->{
                result = size
            }
            MeasureSpec.AT_MOST , MeasureSpec.UNSPECIFIED ->{
                result = (mMeasureWidth  + paddingLeft + paddingRight).toInt();
            }
        }
        //�����AT_MOST,���ܳ��������ֵĳߴ�
        result = if (mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
        return result
    }

    private fun measureHeight(heightMeasureSpec : Int) : Int{
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var result = 0
        when(mode){
            MeasureSpec.EXACTLY ->{
                result = size
            }
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED ->{
                result = (fontMetrics.descent - fontMetrics.ascent + paddingTop + paddingBottom).toInt()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
        return result
    }

}