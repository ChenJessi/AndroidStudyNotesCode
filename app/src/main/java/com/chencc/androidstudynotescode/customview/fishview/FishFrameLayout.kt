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
 * 鱼游动的空间
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
     * 小鱼运动到触摸点的轨迹
     * 小鱼重心点 -->>  手指触摸点
     */
    private fun makeTrail(){
        val fishMiddlePoint = fishDrawable.middlePoint
        val middlePoint = PointF(fishMiddlePoint.x + ivFish.x, fishMiddlePoint.y + ivFish.y)
        // 鱼头圆心的坐标 -- 控制点1
        val fishHeadPoint = fishDrawable.headPoint
        val headPoint = PointF(fishHeadPoint.x + ivFish.x, fishHeadPoint.y + ivFish.y)
        // 第二个控制点坐标
        // 控制点2 为 点C
        //  ∠AOC 的夹角
        val angle = includeAngle(middlePoint, headPoint, PointF(touchX, touchY)) / 2
        // O和控制点2 连线 与 x轴夹角
        val delta = includeAngle(middlePoint , PointF(middlePoint.x + 1f,  middlePoint.y), headPoint)
        // 控制点2 的坐标
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
                // 动画当前执行的百分比
                val fraction = it.animatedFraction
                pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)
                // 根据 x y 的正切值计算出弧度 , 然后转换成角度
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
     * 已知三个角的坐标求角度
     *  原理： 余弦公式
     */
    private fun includeAngle(O : PointF, A : PointF, B : PointF) : Float{
        // 余弦定理
        //  cosO = ( a? + b? - o? ) / 2bc

        /**
         * 设 触摸点为 B  鱼头重心为A 鱼重心为 O  那我们规定 ∠AOB 的一半即角平分线 上的距离 O点 HEAD_RADIUS * 3.2f 长度的位置为控制点 2
         */
        val oaLength = sqrt((A.x - O.x).pow(2) + (A.y - O.y).pow(2))
        val obLength = sqrt((B.x - O.x).pow(2) + (B.y - O.y).pow(2))
        val abLength = sqrt((B.x - A.x).pow(2) + (B.y - A.y).pow(2))

        // 向量积公式
        // 向量数量积公式 ：
        //  a * b = ax * bx + ay * by
        //  a·b=|a||b|·cosθ
        //  cos = a * b / |a| * |b|
        val AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        val cosO = AOB / (oaLength * obLength)

        // ∠ AOB 绝对值
        val angleAOB = Math.toDegrees(acos(cosO).toDouble()).toFloat()

        /**
         *
         * direction = tan∠BAC - tan∠B0C
         * C 点位 经B点做垂线与( Bx, Ay)  ( Bx, Oy) 的交点
         *  ∠α == ∠BAC  ∠β == B0C
         *  根据 tan 正切和 所在象限判断正负
         * 以 AO 中垂线 为准线 作图
         *  控制点B 在 AO 中线的右上方时 ：    ∠α  < ∠β
         *  1. B 在 A 的上方 ：
         *      0  < ∠α  <  90°，  0  < ∠β <  90° &&   tan∠α  <  tan∠β  所以 ：direction =  (-a / -b) - ( -c / -d) < 0
         *  2. B 在 A 的下方：
         *      0  > ∠α > -90° ，  0  < ∠β <  90°  &&  tan∠α  <  tan∠β  所以 ：direction =  (a / -b) - ( -c / -d) < 0
         *
         *
         *  控制点B 在 AO 中线的左上方时 ：  ∠α  < ∠β
         *  1. B 在 A 上方 ：
         *      90°  < ∠α  <  180°， 90°  < ∠β <  180° ,    tan∠α   >  tan∠β   所以  direction =  (-a / b) - ( -c / d) > 0
         *  2. B 在 A 下方 ：
         *      180°  < ∠α  <  270° ,  90°  < ∠β <  180°,   tan∠α   >  tan∠β   所以  direction =  (a / b) - ( -c / d) > 0
         *
         * 控制点B 在 AO 中线的右下方时 ：  |∠α|  > |∠β|
         *  1.  B 在 O 上方 ：
         *      -90° < ∠α < 0°,     0° < ∠β < 90°    tan∠α   <  tan∠β   所以  direction =  (a / -b) - ( -c / -d) < 0
         *  2.  B 在 O 下方 ：
         *      -90° < ∠α < 0°  ,   -90° < ∠β < 0°   tan∠α   <  tan∠β    所以  direction =  (a / -b) - ( c / -d) < 0
         *
         * 控制点B 在 AO 中线的左下方时 ：  |∠α|  > |∠β|
         *  1.  B 在 O 上方 ：
         *       180° < ∠α < 270°,  90° < ∠α < 180°     tan∠α   >  tan∠β   所以  direction =  (a / b) - ( -c / d) > 0
         *  1.  B 在 O 下方 ：
         *       180° < ∠α < 270°,  180° < ∠α < 270°   tan∠α   >  tan∠β    所以  direction =  (a / b) - ( c / d) > 0
         */
        /**
         * 如果控制点大于 90° 说明触摸点在中心点的另一边
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

