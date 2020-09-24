package com.chencc.androidstudynotescode.customview.fishview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import java.lang.Math.pow
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * ���ζ��Ŀռ�
 */
class FishFrameLayout : FrameLayout {

    val fishDrawable by lazy{
        FishDrawable()
    }
    val ivFish by lazy {
        ImageView(context).apply {
            setImageDrawable(fishDrawable)
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }
    }

    val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            color = Color.GRAY
        }
    }
    val mPaint2 by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            color = Color.GRAY
        }
    }
    private var touchX = 0f
    private var touchY = 0f
    var ripple = 0f

    private val animation by lazy {
        ObjectAnimator.ofFloat(this, "ripple", 0f, 110f).apply {
            duration = 3000
        }
    }



    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setWillNotDraw(false)
        addView(ivFish)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        if (ripple <= 90f) {
            mPaint.alpha = ((1 - sin(Math.toRadians(ripple.toDouble()))) * 100).toInt()
            canvas.drawCircle(touchX, touchY, (sin(Math.toRadians(ripple.toDouble())) * 150).toFloat(), mPaint)
        }
        if (ripple >= 20f) {
            mPaint2.alpha = ((1 - sin(Math.toRadians((ripple - 20).toDouble()))) * 100).toInt()
            canvas.drawCircle(touchX, touchY, (sin(Math.toRadians((ripple - 20).toDouble())) * 150).toFloat(), mPaint2)
        }

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event?.x!!
        touchY = event.y
        animation.start()
        return super.onTouchEvent(event)
    }



    /**
     * С���˶���������Ĺ켣
     * С�����ĵ� -->>  ��ָ������
     */
    private fun makeTrail(){
        var middlePoint = fishDrawable.middlePoint
        // ��ͷԲ�ĵ����� -- ���Ƶ�1
        var headPoint = fishDrawable.headPoint
        // �ڶ������Ƶ�����

    }


    /**
     * ��֪�����ǵ�������Ƕ�
     *  ԭ�� ���ҹ�ʽ
     */
    private fun includeAngle(O : PointF, A : PointF, B : PointF){
        // ���Ҷ���
        //  cosO = ( a? + b? - o? ) / 2bc

        /**
         * �� ������Ϊ B  ��ͷ����ΪA ������Ϊ O  �����ǹ涨 ��AOB ��һ�뼴��ƽ���� �ϵľ��� O�� HEAD_RADIUS * 3.2f ���ȵ�λ��Ϊ���Ƶ� 2
         */
        val oaLength = sqrt((A.x - O.x).pow(2) + (A.y - O.y).pow(2))
        val obLength = sqrt((B.x - O.x).pow(2) + (B.y - O.y).pow(2))
        val abLength = sqrt((B.x - A.x).pow(2) + (B.y - A.y).pow(2))

        // ��������ʽ
        // ������������ʽ ��
        //  a * b = ax * bx + ay * by
        //  a��b=|a||b|��cos��
        //  cos = a * b / |a| * |b|

        val cosO = ((A.x - O.x) * (B.x - O.x)  + (A.y - O.y) * (B.y - O.y)) / oaLength * obLength

        // �� AOB ����ֵ
        val angleAOB = Math.toDegrees(acos(cosO).toDouble())

        /**
         *  AO ���ߵ����Ϸ�
         *
         *  tan��ABC - tan��OBC
         */
        val direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x)
    }
}

