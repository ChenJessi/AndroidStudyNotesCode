package com.chencc.androidstudynotescode.draw_text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_LEFT
import com.chencc.androidstudynotescode.utils.sp2px



private const val TAG = "ColorChangeTextView"

const val DIRECTION_LEFT = 0
const val DIRECTION_RIGHT = 1
const val DIRECTION_TOP = 2
const val DIRECTION_BOTTOM = 3
/**
 * 可以改变字体颜色的 TextView
 */
class ColorChangeTextView : View{

    @IntDef(value = [DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_TOP, DIRECTION_BOTTOM])
    @Retention(AnnotationRetention.SOURCE)
    annotation class Direction


    private var mText = "测试"
    private var mTextColor = Color.BLACK
    private var mTextColorChange = Color.RED
    private var mTextSize = 18f.sp2px

    /**
     * 文字画笔
     */
    private val mTextPaint by lazy {
        Paint().apply {
            color = mTextColor
            textSize = mTextSize
            style = Paint.Style.FILL
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }
    }

    private val mLinePaint by lazy {
        Paint().apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
        }
    }

    /**
     * 文字宽高
     */
    private var mTextWidth = 0f
    private var mTextHeight = 0f

    /**
     * 文字位置辅助线
     */
    private val fontMetrics by lazy {
        Paint.FontMetrics()
    }
    private val mTextBounds by lazy { Rect() }

    /**
     * 颜色进度
     */
    private var mPercent = 0f

    /**
     * 变色方向
     */
    @Direction
    private var mDirection = DIRECTION_LEFT

    /**
     * 文字基准点
     */
    private var startTextX = 0f
    private var startTextY = 0f

    /**
     * 裁剪范围
     */
    private val mClipBounds by lazy { Rect() }


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    fun setPercent( percent : Float){
        mPercent = percent
        invalidate()
    }

    fun setTextColor(color : Int){
        mTextColor = color
        invalidate()
    }

    fun setTextChangeColor(color : Int){
        mTextColorChange = color
        invalidate()
    }


    fun setDirection(@Direction direction : Int = DIRECTION_LEFT){
        mDirection = direction
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 测量文字
        measureText()
        // 测量自身大小
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        // 保存测量后的尺寸
        setMeasuredDimension(width, height)

        startTextX = measuredWidth / 2 - mTextWidth / 2
        startTextY = measuredHeight / 2  - fontMetrics.ascent / 2 - fontMetrics.descent / 2

        mTextHeight = fontMetrics.descent - fontMetrics.ascent
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null){
            return
        }

        when(mDirection){
            DIRECTION_LEFT -> {
                /**
                 * 绘制变色后的文字
                 */
                canvas.save();
                mTextPaint.color = mTextColorChange
                canvas.clipRect(startTextX, 0f, startTextX + mTextWidth * mPercent, height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
                /**
                 * 绘制原始文字
                 */
                canvas.save();
                mTextPaint.color = mTextColor
                canvas.clipRect(startTextX + mTextWidth * mPercent, 0f, startTextX + mTextWidth, height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
            }
            DIRECTION_RIGHT -> {
                /**
                 * 绘制变色后的文字
                 */
                canvas.save();
                mTextPaint.color = mTextColorChange
                canvas.clipRect(startTextX + mTextWidth * (1 - mPercent), 0f, startTextX + mTextWidth, height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
                /**
                 * 绘制原始文字
                 */
                canvas.save();
                mTextPaint.color = mTextColor
                canvas.clipRect(startTextX , 0f, startTextX + mTextWidth * (1 - mPercent), height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
            }
            DIRECTION_TOP -> {
                /**
                 * 绘制变色后的文字
                 */
                canvas.save();
                mTextPaint.color = mTextColorChange
                canvas.clipRect(0f, 0f, width.toFloat(), startTextY + fontMetrics.ascent + mTextHeight * mPercent)
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
                /**
                 * 绘制原始文字
                 */
                canvas.save();
                mTextPaint.color = mTextColor
                canvas.clipRect(0f, startTextY + fontMetrics.ascent + mTextHeight * mPercent, width.toFloat(),  height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
            }
            DIRECTION_BOTTOM -> {
                /**
                 * 绘制变色后的文字
                 */
                canvas.save();
                mTextPaint.color = mTextColorChange
                canvas.clipRect(0f, startTextY + fontMetrics.ascent + mTextHeight * (1 - mPercent), width.toFloat(), height.toFloat())
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
                /**
                 * 绘制原始文字
                 */
                canvas.save();
                mTextPaint.color = mTextColor
                canvas.clipRect(0f, 0f, width.toFloat(),  startTextY + fontMetrics.ascent + mTextHeight * (1 - mPercent))
                canvas.getClipBounds(mClipBounds)
                canvas.drawRect(mClipBounds, mLinePaint)
                canvas.drawText(mText, startTextX, startTextY, mTextPaint)
                canvas.restore()
                mClipBounds.setEmpty()
            }
        }

    }


    /**
     * 测量文字
     */
    private fun measureText(){
        // 计算文字宽高
        mTextWidth = mTextPaint.measureText(mText)
        //获取文字辅助线
        mTextPaint.getFontMetrics(fontMetrics)
        // 获取文字边界
        mTextPaint.getTextBounds(mText, 0, mText.length, mTextBounds)
    }

    /**
     * 测量宽高
     */
    private fun measureWidth(widthMeasureSpec: Int) : Int{
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var result = 0
        when(mode){
            MeasureSpec.EXACTLY -> {
                result = size
            }
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                result = (mTextWidth + paddingLeft + paddingRight).toInt()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
        return result
    }

    private fun measureHeight(heightMeasureSpec: Int) : Int{
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var result = 0
        when(mode){
            MeasureSpec.EXACTLY -> {
                result = size
            }
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                result = (fontMetrics.descent - fontMetrics.ascent + paddingTop + paddingBottom).toInt()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
        return result
    }
}