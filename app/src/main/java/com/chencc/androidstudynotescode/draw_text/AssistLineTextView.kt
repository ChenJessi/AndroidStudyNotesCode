package com.chencc.androidstudynotescode.draw_text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

private const val TAG = "AssistLineTextView"
/**
 * 绘制辅助线文字
 */
class AssistLineTextView : AppCompatTextView {

    private var mText = "文字绘制演示"

    private val mTextPaint by lazy {
        Paint().apply {
            color = Color.RED
            textSize = 180f
            textAlign = Paint.Align.LEFT
        }
    }

    private val mLinePaint by lazy {
        Paint().apply {
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }
    }
    private val mDashPathEffect by lazy {
        DashPathEffect(floatArrayOf(3f, 2f), 0f);
    }
    private val mLintPath by lazy {
        Path()
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null){
            return
        }
        val textWidth = mTextPaint.measureText(mText)
        val metrics = mTextPaint.fontMetrics
        val textHeight = metrics.descent - metrics.ascent
//        val baseLine = height / 2 + textHeight / 2 - metrics.descent  简化如下
        val baseLine = height / 2 - ( metrics.descent + metrics.ascent )/ 2

        drawHorizontal(canvas)
        drawVertical(canvas)
        drawBaseLine(canvas, baseLine)
        drawAssistLine(canvas, metrics, baseLine)
//        canvas.save()
        val textX = width / 2 - textWidth / 2
        canvas.drawText(mText, textX, baseLine, mTextPaint)


    }

    /**
     * 绘制横线
     */
    private fun drawHorizontal(canvas: Canvas){
        canvas.save()
        mLinePaint.pathEffect = mDashPathEffect
        mLinePaint.color = Color.GRAY
        mLintPath.reset()
        mLintPath.moveTo(0f, height / 2f)
        mLintPath.lineTo( width.toFloat(), height / 2f )
        canvas.drawPath(mLintPath, mLinePaint)
        canvas.restore()
    }

    /**
     * 绘制竖线
     */
    private fun drawVertical(canvas: Canvas){
        canvas.save()
        mLinePaint.pathEffect = DashPathEffect(floatArrayOf(5f, 2f), 0f);
        mLinePaint.color = Color.GRAY
        mLintPath.reset()
        mLintPath.moveTo(width / 2f, 0f)
        mLintPath.lineTo( width / 2f, height.toFloat() )
//        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), mLinePaint)
        canvas.drawPath(mLintPath, mLinePaint)
        canvas.restore()
    }

    /**
     *  绘制 baseline
     */
    private fun drawBaseLine(canvas: Canvas , baseLine : Float){
        canvas.save()
        //  文字高度
        mLinePaint.pathEffect = null
        mLinePaint.color = Color.RED
        canvas.drawLine(0f, baseLine, width.toFloat(), baseLine, mLinePaint)
        canvas.restore()
    }

    /**
     *  绘制 其他辅助线
     */
    private fun drawAssistLine(canvas: Canvas , metrics : Paint.FontMetrics, baseLine : Float){
        canvas.save()

        Log.e(TAG, "drawAssistLine: ${metrics.top}" )
        val top = baseLine + metrics.top
        val bottom = baseLine + metrics.bottom
        val ascent = baseLine + metrics.ascent
        val descent = baseLine + metrics.descent
        val leading = baseLine + metrics.leading

        mLinePaint.style = Paint.Style.FILL
        mLinePaint.textSize = 50f
        val textHeight = mLinePaint.fontMetrics.descent -  mLinePaint.fontMetrics.ascent

        mLinePaint.color = Color.GREEN
        canvas.drawLine(0f, top , width.toFloat(), top, mLinePaint)
        canvas.drawText("top", 0f, height.toFloat() - textHeight * 3 - 20, mLinePaint)
        mLinePaint.color = Color.BLUE
        canvas.drawLine(0f, bottom, width.toFloat(), bottom, mLinePaint)
        canvas.drawText("bottom", 0f, height.toFloat() - textHeight * 2 - 20, mLinePaint)
        mLinePaint.color = Color.YELLOW
        canvas.drawLine(0f, ascent, width.toFloat(), ascent, mLinePaint)
        canvas.drawText("ascent", 0f, height.toFloat() - textHeight * 1 -20, mLinePaint)
        mLinePaint.color = Color.CYAN
        canvas.drawLine(0f, descent, width.toFloat(), descent, mLinePaint)
        canvas.drawText("descent", 0f, height.toFloat() - textHeight * 0 - 20, mLinePaint)

        mLinePaint.color = Color.DKGRAY
        canvas.drawLine(0f, leading, width.toFloat() / 2, leading, mLinePaint)
        canvas.drawText("leading", 0f, height.toFloat() - textHeight * 4 - 20, mLinePaint)



        mLinePaint.color = Color.RED
        canvas.drawText("baseLine", 0f, height.toFloat() - textHeight * 5 - 20, mLinePaint)


        canvas.restore()
    }
}
