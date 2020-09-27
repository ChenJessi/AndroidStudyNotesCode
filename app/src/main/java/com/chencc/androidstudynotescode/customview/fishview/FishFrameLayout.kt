package com.chencc.androidstudynotescode.customview.fishview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import java.lang.Math.pow
import kotlin.math.*


private const val TAG = "FishFrameLayout"
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

    val mPaint =
        Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            color = Color.GRAY
        }

    val mPath = Path()
    private var touchX = 0f
    private var touchY = 0f
    var ripple = 0f

    private val animation=
        ObjectAnimator.ofFloat(this, "ripple", 0f, 110f).apply {
            duration = 3000
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
            mPaint.alpha = ((1 - sin(Math.toRadians((ripple - 20).toDouble()))) * 100).toInt()
            canvas.drawCircle(touchX, touchY, (sin(Math.toRadians((ripple - 20).toDouble())) * 150).toFloat(), mPaint)
        }

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event?.x!!
        touchY = event.y
        animation.start()
        makeTrail()
        return super.onTouchEvent(event)
    }



    /**
     * С���˶���������Ĺ켣
     * С�����ĵ� -->>  ��ָ������
     */
    private fun makeTrail(){
        val fishMiddlePoint = fishDrawable.middlePoint
        val middlePoint = PointF(fishMiddlePoint.x + ivFish.x, fishMiddlePoint.y + ivFish.y)
        // ��ͷԲ�ĵ����� -- ���Ƶ�1
        val fishHeadPoint = fishDrawable.headPoint
        val headPoint = PointF(fishHeadPoint.x + ivFish.x, fishHeadPoint.y + ivFish.y)
        // �ڶ������Ƶ�����
        // ���Ƶ�2 Ϊ ��C
        //  ��AOC �ļн�
        val angle = includeAngle(middlePoint, headPoint, PointF(touchX, touchY)) / 2
        // O�Ϳ��Ƶ�2 ���� �� x��н�
        val delta = includeAngle(middlePoint , PointF(middlePoint.x + 1f,  middlePoint.y), headPoint)
        // ���Ƶ�2 ������
        val controlPoint = fishDrawable.calculatePoint(middlePoint, fishDrawable.HEAD_RADIUS * 1.5f, angle + delta)

        mPath.reset()
        mPath.moveTo(middlePoint.x - fishMiddlePoint.x, middlePoint.y - fishMiddlePoint.y)
        mPath.cubicTo(headPoint.x - fishMiddlePoint.x, headPoint.y - fishMiddlePoint.x,
                    controlPoint.x - fishMiddlePoint.x, controlPoint.y - fishMiddlePoint.x,
                    touchX - fishMiddlePoint.x, touchY- fishMiddlePoint.x)

        val pathMeasure = PathMeasure(mPath , false)
        val tan = floatArrayOf(0f, 0f)
        ObjectAnimator.ofFloat(ivFish, "x", "y", mPath).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
            addUpdateListener {
                // ������ǰִ�еİٷֱ�
                val fraction = it.animatedFraction
                pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)
                // ���� x y ������ֵ��������� , Ȼ��ת���ɽǶ�
                val fishAngle = Math.toDegrees(atan2(-tan[1], tan[0]).toDouble())
                fishDrawable.fishMainAngle = fishAngle.toFloat()
            }
        }.addListener( object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                fishDrawable.frequence = 3f
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                fishDrawable.frequence = 1f
            }
        })




    }


    /**
     * ��֪�����ǵ�������Ƕ�
     *  ԭ�� ���ҹ�ʽ
     */
    private fun includeAngle(O : PointF, A : PointF, B : PointF) : Float{
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
        val AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        val cosO = AOB / (oaLength * obLength)

        // �� AOB ����ֵ
        val angleAOB = Math.toDegrees(acos(cosO).toDouble()).toFloat()

        /**
         *
         * direction = tan��BAC - tan��B0C
         * C ��λ ��B����������( Bx, Ay)  ( Bx, Oy) �Ľ���
         *  �Ϧ� == ��BAC  �Ϧ� == B0C
         *  ���� tan ���к� ���������ж�����
         * �� AO �д��� Ϊ׼�� ��ͼ
         *  ���Ƶ�B �� AO ���ߵ����Ϸ�ʱ ��    �Ϧ�  < �Ϧ�
         *  1. B �� A ���Ϸ� ��
         *      0  < �Ϧ�  <  90�㣬  0  < �Ϧ� <  90�� &&   tan�Ϧ�  <  tan�Ϧ�  ���� ��direction =  (-a / -b) - ( -c / -d) < 0
         *  2. B �� A ���·���
         *      0  > �Ϧ� > -90�� ��  0  < �Ϧ� <  90��  &&  tan�Ϧ�  <  tan�Ϧ�  ���� ��direction =  (a / -b) - ( -c / -d) < 0
         *
         *
         *  ���Ƶ�B �� AO ���ߵ����Ϸ�ʱ ��  �Ϧ�  < �Ϧ�
         *  1. B �� A �Ϸ� ��
         *      90��  < �Ϧ�  <  180�㣬 90��  < �Ϧ� <  180�� ,    tan�Ϧ�   >  tan�Ϧ�   ����  direction =  (-a / b) - ( -c / d) > 0
         *  2. B �� A �·� ��
         *      180��  < �Ϧ�  <  270�� ,  90��  < �Ϧ� <  180��,   tan�Ϧ�   >  tan�Ϧ�   ����  direction =  (a / b) - ( -c / d) > 0
         *
         * ���Ƶ�B �� AO ���ߵ����·�ʱ ��  |�Ϧ�|  > |�Ϧ�|
         *  1.  B �� O �Ϸ� ��
         *      -90�� < �Ϧ� < 0��,     0�� < �Ϧ� < 90��    tan�Ϧ�   <  tan�Ϧ�   ����  direction =  (a / -b) - ( -c / -d) < 0
         *  2.  B �� O �·� ��
         *      -90�� < �Ϧ� < 0��  ,   -90�� < �Ϧ� < 0��   tan�Ϧ�   <  tan�Ϧ�    ����  direction =  (a / -b) - ( c / -d) < 0
         *
         * ���Ƶ�B �� AO ���ߵ����·�ʱ ��  |�Ϧ�|  > |�Ϧ�|
         *  1.  B �� O �Ϸ� ��
         *       180�� < �Ϧ� < 270��,  90�� < �Ϧ� < 180��     tan�Ϧ�   >  tan�Ϧ�   ����  direction =  (a / b) - ( -c / d) > 0
         *  1.  B �� O �·� ��
         *       180�� < �Ϧ� < 270��,  180�� < �Ϧ� < 270��   tan�Ϧ�   >  tan�Ϧ�    ����  direction =  (a / b) - ( c / d) > 0
         */
        /**
         * ������Ƶ���� 90�� ˵�������������ĵ����һ��
         */
        val direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x)

        return if (direction == 0f){
            if (AOB >= 0){
                0f
            }else {
                180f
            }
        }else{
            if (direction > 0){
                -angleAOB
            }else{
                angleAOB
            }
        }

    }
}

